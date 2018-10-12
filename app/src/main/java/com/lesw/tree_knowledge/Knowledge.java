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
import java.util.Set;
import java.util.TreeSet;

@Entity(tableName = "knowledge")
public class Knowledge {

    public static Knowledge ROOT = null;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @Ignore
    private int count = 0;

    @Ignore
    private int warningCount = 1;

    @ColumnInfo(name = "up")
    private int up;

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

    @Ignore
    public Knowledge(String name) {
        this.name = name;
        this.up = 0;
        this.childrenSetStr = gson.toJson(new TreeSet<Integer>(), setType);
    }

    @Ignore
    public Knowledge(String name, int up) {
        this.name = name;
        this.up = up;
        this.childrenSetStr = gson.toJson(new TreeSet<Integer>(), setType);
    }

    public Knowledge(String name, int up, String childrenSetStr) {
        this.name = name;
        this.up = up;
        this.childrenSetStr = childrenSetStr;
    }

    public void manageUp(Context context) {
        Knowledge knowledge = RoomDbUtils.getKnowledgeById(this.up, context);

        if (knowledge != null) {
            knowledge.addChild(this);
            RoomDbUtils.updateKnowledgeByChildren(knowledge, context);
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

    public String getChildrenSetStr() {
        return childrenSetStr;
    }

    public void populateChildren(Context context) {
        Set<Integer> set = gson.fromJson(childrenSetStr, setType);

        for (int i : set) {
            Knowledge k = RoomDbUtils.getKnowledgeById(i, context);
            this.addChild(k);
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

        Knowledge knowledge = RoomDbUtils.getKnowledgeById(this.up, context);
        return knowledge != null && knowledge.blackLeaf(context);
    }

    private boolean redLeaf(){
        return (getCount() <= getWarningCount());
    }

    void count(Employee employee, Context context) {
        employeeSet.add(employee);
        count = employeeSet.size();

        Knowledge knowledge = RoomDbUtils.getKnowledgeById(this.up, context);

        if (knowledge != null) {
            knowledge.count(employee, context);
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
        Knowledge rootKnowledge = employee.getRootKnowledge(context);
        Set<Knowledge> knowledgeSet = employee.getKnowledgeSet();
        Set<Integer> set = gson.fromJson(employee.getKnowledgeSetStr(), setType);

        if(rootKnowledge == null) return new TreeNode(new IconTreeItem(R.string.fa_leaf,
                "Árvore do Conhecimento",
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf,
                rootKnowledge.getName(),
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        for (Knowledge child : rootKnowledge.getChildren()) {
            if (set.contains(child.getId())) {
                root.addChild(generateUserItem(child, knowledgeSet));
            }
        }

        return root;
    }

    private static TreeNode generateUserItem(Knowledge knowledge, Set<Knowledge> knowledgeSet){
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, knowledge.getName(),
                ColorEnum.GREEN, null));

        for (Knowledge child : knowledge.getChildren()) {
            if(knowledgeSet.contains(child)) {
                node.addChild(generateUserItem(child, knowledgeSet));
            }
        }

        return node;
    }
}
