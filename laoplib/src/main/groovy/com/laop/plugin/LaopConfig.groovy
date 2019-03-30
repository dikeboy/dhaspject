package com.laop.plugin

import com.laop.plugin.LaopUtils

class LaopConfig{
    List<String> kotlinFiles = new ArrayList<>()
    List<String> javaFiles = new ArrayList<>()
    String aopModule= new String()
    String aopType=  LaopUtils.AOP_TYPE_DEFAULT // debug, close, default
    int  moduleType =1 //1default or main app,  2 library module

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
    LaopConfig moduleType(int type) {
        if (type != null) {
            moduleType = type
        }
        return this
    }
}