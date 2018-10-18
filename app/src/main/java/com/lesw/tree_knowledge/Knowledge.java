package com.lesw.tree_knowledge;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unnamed.b.atv.model.TreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.annotation.CheckForSigned;

@Entity(tableName = "knowledge")
public class Knowledge {

    public static Knowledge ROOT = null;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "count")
    private int count = 0;

    @Ignore
    private int warningCount = 1;

    @ColumnInfo(name = "up")
    private int up;

    @ColumnInfo(name = "level")
    private int level;

    @Ignore
    private static Type setType = new TypeToken<Set<Integer>>(){}.getType();

    @Ignore
    private static Gson gson = new Gson();

    @Ignore
    private List<Knowledge> children = new ArrayList<>();

    @ColumnInfo(name = "children_set")
    private String childrenSetStr;

    @Ignore
    private Set<Employee> employeeSet = new HashSet<>();

    @ColumnInfo(name = "employee_set")
    private String employeeSetStr;

    @Ignore
    public Knowledge(String name) {
        this.name = name;
        this.up = 0;
        this.level = 0;
        this.count = 0;
        this.childrenSetStr = gson.toJson(new TreeSet<Integer>(), setType);
        this.employeeSetStr = gson.toJson(new TreeSet<Integer>(), setType);
    }

    @Ignore
    public Knowledge(String name, int up, int level) {
        this.name = name;
        this.up = up;
        this.level = level;
        this.count = 0;
        this.childrenSetStr = gson.toJson(new TreeSet<Integer>(), setType);
        this.employeeSetStr = gson.toJson(new TreeSet<Integer>(), setType);
    }

    public Knowledge(String name, int up, int level, int count, String childrenSetStr, String employeeSetStr) {
        this.name = name;
        this.up = up;
        this.level = level;
        this.count = count;
        this.childrenSetStr = childrenSetStr;
        this.employeeSetStr = employeeSetStr;

        Set<Integer> set = gson.fromJson(this.employeeSetStr, setType);

        for (int id : set) {
            Employee employee = DummyDB.getInstance().findEmployeeById(id);

            employeeSet.add(employee);
        }
    }

    public void manageUp(Context context) {
        Knowledge knowledge = Knowledge.getKnowledgeFromList(this.up);

        if (knowledge != null && knowledge.getUp() >= 0) {
            knowledge.addChild(this);
            RoomDbUtils.getInstance().updateKnowledgeByChildren(knowledge);
        }
    }

    public static Knowledge getById(int id, Knowledge root){
        List<Knowledge> knowledgeList = getKnowledgeList(root);
        for (Knowledge knowledge : knowledgeList) {
            if(knowledge.getId() == id) return knowledge;
        }
        return null;
    }

    private static List<Knowledge> getKnowledgeList(Knowledge root){
        ArrayList<Knowledge> knowledgeList = new ArrayList<>();
        generateKnowledgeList(knowledgeList, root);
        return knowledgeList;
    }

    private static void generateKnowledgeList(List<Knowledge> list, Knowledge knowledge){
        list.add(knowledge);
        for (Knowledge child : knowledge.getChildren()) {
            generateKnowledgeList(list, child);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    private List<Knowledge> getChildren() {
        return children;
    }

    private void addChild(Knowledge knowledge) {
        children.add(knowledge);

        Set<Integer> set = new TreeSet<>();
        for (Knowledge k : children) {
            if (k != null) {
                set.add(k.getId());
            }
        }

        this.childrenSetStr = gson.toJson(set, setType);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getChildrenSetStr() {
        return childrenSetStr;
    }

    public String getEmployeeSetStr() {
        return employeeSetStr;
    }

    public void setEmployeeSetStr(String employeeSetStr) {
        this.employeeSetStr = employeeSetStr;
    }

    public static Knowledge getKnowledgeFromList(int id) {
        for (Knowledge k : DummyDB.getInstance().getCompanyKnowledgeList()) {
            if (k.getId() == id) {
                return k;
            }
        }

        return new Knowledge("NotFound!", -1, 0);
    }

    private Map<Integer, Knowledge> getMapFromList(List<Knowledge> knowledgeList) {
        TreeMap<Integer, Knowledge> map = new TreeMap<>();

        for (Knowledge k : knowledgeList) {
            map.put(k.getId(), k);
        }

        return map;
    }

    public void populateChildren(List<Knowledge> knowledgeList) {
        Map<Integer, Knowledge> map = this.getMapFromList(knowledgeList);

        Set<Integer> set = gson.fromJson(childrenSetStr, setType);

        for (int i : set) {
            if (map.containsKey(i)) {
                Knowledge k = map.get(i);

                if (k.getUp() > 0) {
                    this.addChild(k);
                }
            }
        }
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    private boolean greenLeaf(){
        return (getCount() > getWarningCount());
    }

    private boolean blackLeaf(Context context){
        for (Knowledge child : children) {
            if(child.redLeaf()) return true;
        }

        Knowledge knowledge = Knowledge.getKnowledgeFromList(this.getUp());
        return knowledge != null && knowledge.blackLeaf(context);
    }

    private boolean redLeaf(){
        return (getCount() <= getWarningCount());
    }

    void count(Employee employee) {
        employeeSet.add(employee);

        Set<Integer> set = gson.fromJson(this.employeeSetStr, setType);

        set.add(employee.getId());

        employeeSetStr = gson.toJson(set, setType);
        count = employeeSet.size();

        RoomDbUtils.getInstance().updateKnowledgeByCountingAndEmployee(this);

        Knowledge knowledge = Knowledge.getKnowledgeFromList(this.getUp());

        if (knowledge.getUp() >= 0) {
            knowledge.count(employee);
        }
    }

    static TreeNode generateHRTree(Knowledge knowledge, Context context){
        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf, ColorEnum.GREEN, knowledge)).setViewHolder(new TreeHolder(context));
        for (Knowledge child : knowledge.getChildren()) {
            root.addChild(generateHRItem(child));
        }
        return root;
    }

    private static TreeNode generateHRItem(Knowledge knowledge){
        ColorEnum color;
        color = ColorEnum.GREEN;
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, color, knowledge));
        for (Knowledge child : knowledge.getChildren()) {
            node.addChild(generateHRItem(child));
        }
        return node;
    }

    static TreeNode generateManagerTree(Knowledge knowledge, Context context){
        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf, ColorEnum.GREEN, knowledge)).setViewHolder(new TreeHolder(context));
        for (Knowledge child : knowledge.getChildren()) {
            root.addChild(generateManagerItem(child, context));
        }
        return root;
    }

    private static TreeNode generateManagerItem(Knowledge knowledge, Context context){
        ColorEnum color;
        if(knowledge.redLeaf()){
            color = ColorEnum.RED;
        } else if(knowledge.blackLeaf(context)){
            color = ColorEnum.BLACK;
        } else {
            color = ColorEnum.GREEN;
        }
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, color, knowledge));
        for (Knowledge child : knowledge.getChildren()) {
            node.addChild(generateManagerItem(child, context));
        }
        return node;
    }

    static TreeNode generateUserTree(Employee employee, Context context){
        Knowledge rootKnowledge = employee.getRootKnowledge();
        Set<Knowledge> knowledgeSet = employee.getKnowledgeSet();
        Set<Integer> set = gson.fromJson(employee.getKnowledgeSetStr(), setType);

        if(rootKnowledge == null) return new TreeNode(new IconTreeItem(R.string.fa_leaf,
                "Árvore do Conhecimento",
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf,
                rootKnowledge.getName(),
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        for (Knowledge child : rootKnowledge.getChildren()) {
            if (child != null && set.contains(child.getId())) {
                root.addChild(generateUserItem(child, set));
            }
        }

        return root;
    }

    private static TreeNode generateUserItem(Knowledge knowledge, Set<Integer> set){
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, knowledge.getName(),
                ColorEnum.GREEN, null));

        for (Knowledge child : knowledge.getChildren()) {
            if(child != null && set.contains(child.getId())) {
                node.addChild(generateUserItem(child, set));
            }
        }

        return node;
    }
}
