package com.laop.inter

import org.gradle.api.tasks.compile.JavaCompile

abstract  class  IAspect{
    abstract  void  doAsepct(String taskName, JavaCompile javaCompile,String aspectpath,List<String> kotlinAspectInPath)
}