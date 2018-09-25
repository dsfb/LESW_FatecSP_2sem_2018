package com.lesw.tree_knowledge;

import android.content.Context;
import android.view.Display;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "Knowledge")
public class Knowledge extends Model {

    public final static Knowledge ROOT = new Select().from(Knowledge.class).where("id = ?", 1).executeSingle();

    @Column(name = "name")
    private String name;

    private int count = 0;
    private int warningCount = 1;

    @Column(name = "up")
    private Long up;

    private List<Knowledge> children = new ArrayList<>();
    private Set<Employee> employeeSet = new HashSet<>();

    public Knowledge() {

    }

    public Knowledge(String name, Long up) {
        this.name = name;
        this.up = up;

        Knowledge knowledge = new Select().from(Knowledge.class).where("id = ?", this.up).executeSingle();

        if (knowledge != null) {
            knowledge.addChild(this);
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

    private List<Knowledge> getChildren() {
        return this.children;
    }

    private void addChild(Knowledge knowledge) {
        this.children.add(knowledge);
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

    public Long getUp() {
        return up;
    }

    public void setUp(Long up) {
        this.up = up;
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

    private boolean blackLeaf(){
        for (Knowledge child : children) {
            if(child.redLeaf()) return true;
        }

        Knowledge knowledge = new Select().from(Knowledge.class).where("id = ?", up).executeSingle();
        return knowledge != null && knowledge.blackLeaf();
    }

    private boolean redLeaf(){
        return (getCount() <= getWarningCount());
    }

    void count(Employee employee) {
        employeeSet.add(employee);
        count = employeeSet.size();

        Knowledge knowledge = new Select().from(Knowledge.class).where("id = ?", up).executeSingle();

        knowledge.count(employee);
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
            root.addChild(generateManagerItem(child));
        }
        return root;
    }

    private static TreeNode generateManagerItem(Knowledge knowledge){
        ColorEnum color;
        if(knowledge.redLeaf()){
            color = ColorEnum.RED;
        } else if(knowledge.blackLeaf()){
            color = ColorEnum.BLACK;
        } else {
            color = ColorEnum.GREEN;
        }
        TreeNode node = new TreeNode(new IconTreeItem(R.string.fa_leaf, color, knowledge));
        for (Knowledge child : knowledge.getChildren()) {
            node.addChild(generateManagerItem(child));
        }
        return node;
    }

    static TreeNode generateUserTree(Employee employee, Context context){
        Knowledge rootKnowledge = employee.getRootKnowledge();
        Set<Knowledge> knowledgeSet = employee.getKnowledgeSet();

        if(rootKnowledge == null) return new TreeNode(new IconTreeItem(R.string.fa_leaf,
                "Árvore do Conhecimento",
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        TreeNode root = new TreeNode(new IconTreeItem(R.string.fa_leaf,
                rootKnowledge.getName(),
                ColorEnum.GREEN, null)).setViewHolder(new TreeHolder(context));

        for (Knowledge child : rootKnowledge.getChildren()) {
            if(knowledgeSet.contains(child)) {
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
