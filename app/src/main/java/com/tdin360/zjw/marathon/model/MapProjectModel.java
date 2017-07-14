package com.tdin360.zjw.marathon.model;

import java.util.List;

/**
 *
 * 赛事路线项目数据模型
 * Created by admin on 17/3/2.
 */

public class MapProjectModel {


    /**
     * 赛事名称
     */
    private String projectName;
    /**
     * 络线途经点
     */
    private List<MapPointNode> nodes;

    /**
     * 起点
     */
    private MapPointNode startNode;
    /**
     * 终点
     */
    private MapPointNode endNode;

    public MapProjectModel(String projectName, List<MapPointNode> nodes, MapPointNode startNode, MapPointNode endNode) {
        this.projectName = projectName;
        this.nodes = nodes;
        this.startNode = startNode;
        this.endNode = endNode;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<MapPointNode> getNodes() {
        return nodes;
    }

    public MapPointNode getStartNode() {
        return startNode;
    }

    public MapPointNode getEndNode() {
        return endNode;
    }

    public void setEndNode(MapPointNode endNode) {
        this.endNode = endNode;
    }

    public void setStartNode(MapPointNode startNode) {
        this.startNode = startNode;
    }

    @Override
    public String toString() {
        return this.projectName;
    }
}
