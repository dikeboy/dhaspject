package com.laop.plugin

import com.laop.plugin.LaopUtils

class LaopConfig{
    List<String> kotlinFiles = new ArrayList<>()
    List<String> javaFiles = new ArrayList<>()
    String aopModule= new String()
    String aopType=  LaopUtils.AOP_TYPE_DEFAULT // debug, close, default
    boolean   hasFlavors = false
    boolean  useJavaTask = false  //If kotlin not exist(operation)

    LaopConfig javafiles(String...filters) {
        if (filters != null) {
            this.javaFiles.addAll(filters)
        }

        return this
    }

    LaopConfig kotlinfiles(String...filters) {
        if (filters != null) {
            this.kotlinFiles.addAll(filters)
        }
        return this
    }

    LaopConfig aopPath(String path) {
        if (path != null) {
            aopPath = path
        }
        return this
    }
    LaopConfig aopType(String type) {
        if (type != null) {
            aopType = type
        }
        return this
    }
    LaopConfig hasFlavors(boolean type) {
         hasFlavors = type
        return this
    }
    LaopConfig useJavaTask(boolean type) {
        useJavaTask = type
        return this
    }
}