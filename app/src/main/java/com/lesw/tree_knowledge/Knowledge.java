package com.lesw.tree_knowledge;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Entity(tableName = "knowledge")
public class Knowledge {

    public static Knowledge ROOT = null;

    // TODO: make it autoincrementable...!
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "count")
    private int count = 0;

    @ColumnInfo(name = "warning_count")
    private int warningCount = 1;

    @ColumnInfo(name = "up")
    private int up;

    @ColumnInfo(name = "level")
    private int level;

    @ColumnInfo(name = "children_set")
    private Set<Integer> childrenSet;

    @ColumnInfo(name = "employee_set")
    private Set<Integer> employeeSet;

    @Ignore
    public Knowledge(String name) {
        this(name, 0, 0);
    }

    @Ignore
    public Knowledge(String name, int up, int level) {
        this(name, up, level, 0, 1, new TreeSet<Integer>(), new TreeSet<Integer>());
    }

    public Knowledge(String name, int up, int level, int count, int warningCount, Set<Integer> childrenSet, Set<Integer> employeeSet) {
        this.name = name;
        this.up = up;
        this.level = level;
        this.count = count;
        this.warningCount = warningCount;
        this.childrenSet = childrenSet;
        this.employeeSet = employeeSet;
    }

    public void manageUp(Context context) {
        Knowledge knowledge = Knowledge.getKnowledgeFromId(this.up);

        if (knowledge != null && knowledge.getUp() >= 0) {
            knowledge.addChild(this);
            RoomDbManager.getInstance().updateKnowledgeByChildren(knowledge);
        }
    }

    public static Knowledge getById(int id){
        return RoomDbManager.getInstance().getKnowledgeById(id);
    }

    private static void generateKnowledgeList(List<Knowledge> list, Knowledge knowledge){
        list.add(knowledge);
        for (Integer childId : knowledge.getChildrenSet()) {
            generateKnowledgeList(list, RoomDbManager.getInstance().getKnowledgeById(childId));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public Set<Integer> getChildrenSet() {
        return this.childrenSet;
    }

    private void addChild(Knowledge knowledge) {
        if (knowledge != null && knowledge.getId() > 0) {
            this.childrenSet.add(knowledge.getId());
        }
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

    public void setChildrenSet(Set<Integer> childrenSet) {
        this.childrenSet = childrenSet;
    }

    public Set<Integer> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<Integer> employeeSet) {
        this.employeeSet = employeeSet;
    }

    public static Knowledge getKnowledgeFromId(int id) {
        return RoomDbManager.getInstance().getKnowledgeById(id);
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
        RoomDbManager.getInstance().updateKnowledgeByWarningCount(this);
    }

    private boolean greenLeaf(){
        return (getCount() > getWarningCount());
    }

    private boolean blackLeaf(Context context){
        for (Integer childId : this.childrenSet) {
            Knowledge child = RoomDbManager.getInstance().getKnowledgeById(childId);
            if(child.redLeaf()) return true;
        }

        Knowledge knowledge = Knowledge.getKnowledgeFromId(this.getUp());
        return knowledge != null && knowledge.getUp() > -1 && knowledge.blackLeaf(context);
    }

    private boolean redLeaf(){
        return (getCount() <= getWarningCount());
    }

    void count(Employee employee) {
        this.employeeSet.add(employee.getId());

        count = this.employeeSet.size();

        RoomDbManager.getInstance().updateKnowledgeByCountingAndEmployee(this);

        Knowledge knowledge = Knowledge.getKnowledgeFromId(this.getUp());

        if (knowledge != null && knowledge.getUp() >= 0) {
            knowledge.count(employee);
        }
    }

    static TreeNode generateHRTree(Knowledge knowledge, Context context){
        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf, ColorEnum.GREEN, knowledge)).setViewHolder(new TreeHolder(context));
        for (Integer childId : knowledge.getChildrenSet()) {
            root.addChild(generateHRItem(RoomDbManager.getInstance().getKnowledgeById(childId)));
        }
        return root;
    }

    private static TreeNode generateHRItem(Knowledge knowledge){
        ColorEnum color;
        color = ColorEnum.GREEN;
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, color, knowledge));
        for (Integer childId : knowledge.getChildrenSet()) {
            node.addChild(generateHRItem(RoomDbManager.getInstance().getKnowledgeById(childId)));
        }
        return node;
    }

    static TreeNode generateManagerTree(Knowledge knowledge, Context context){
        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf, ColorEnum.GREEN, knowledge)).setViewHolder(new TreeHolder(context));
        for (Integer childId : knowledge.getChildrenSet()) {
            root.addChild(generateManagerItem(RoomDbManager.getInstance().getKnowledgeById(childId), context));
        }
        return root;
    }

    private static TreeNode generateManagerItem(Knowledge knowledge, Context context){
        ColorEnum color;
        if (knowledge.redLeaf()) {
            color = ColorEnum.RED;
        } else if (knowledge.blackLeaf(context)) {
            color = ColorEnum.BLACK;
        } else {
            color = ColorEnum.GREEN;
        }

        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, color, knowledge));
        for (Integer childId : knowledge.getChildrenSet()) {
            node.addChild(generateManagerItem(RoomDbManager.getInstance().getKnowledgeById(childId), context));
        }
        return node;
    }

    static TreeNode generateUserTree(Employee employee, Context context){
        Knowledge rootKnowledge = Knowledge.ROOT;
        Set<Integer> set = employee.getKnowledgeSet();

        if (rootKnowledge == null) return new TreeNode(new IconTreeItem(R.string.fa_leaf,
                "Árvore do Conhecimento",
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf,
                rootKnowledge.getName(),
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        for (Integer childId : rootKnowledge.getChildrenSet()) {
            Knowledge child = RoomDbManager.getInstance().getKnowledgeById(childId);
            if (child != null && set.contains(child.getId())) {
                root.addChild(generateUserItem(child, set));
            }
        }

        return root;
    }

    private static TreeNode generateUserItem(Knowledge knowledge, Set<Integer> set){
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, knowledge.getName(),
                ColorEnum.GREEN, null));

        for (Integer childId : knowledge.getChildrenSet()) {
            Knowledge child = RoomDbManager.getInstance().getKnowledgeById(childId);

            if (child != null && set.contains(child.getId())) {
                node.addChild(generateUserItem(child, set));
            }
        }

        return node;
    }
}
