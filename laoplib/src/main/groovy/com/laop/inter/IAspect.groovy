package com.laop.inter

import com.laop.plugin.LaopConfig
import org.gradle.api.tasks.compile.JavaCompile

abstract  class  IAspect{
    abstract  void  doAsepct(String taskName, JavaCompile javaCompile, LaopConfig laopConfig, List<String> kotlinAspectInPath,String aspectpath)
}