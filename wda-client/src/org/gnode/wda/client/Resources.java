package org.gnode.wda.client;

import java.util.Arrays;
import java.util.List;

public class Resources {
	/*
	 * The resources file aims to be like a .rc file in Unix-like systems. 
	 * It will provide configurability to WDAT. As more and more configurables
	 * are exposed in the WDAT system, they will be added as static variables 
	 * or functions to this file. 
	 * 
	 */
	
	public static List<String> colors = Arrays.asList("#edc240", "#afd8f8", "#cb4b4b", "#4da74d", "#9440ed",
													  "#51967A", "#61B02C", "#4D2359", "#BD2067", "#948D2E", "#3497C2", "#1D5EB3");
	// colors are used while rendering graphs. 
	
	public static Double staticRatio = 0.2;   // These ratios are used to fix
	public static Double masterRatio = 0.25;  // the heights of static-master-
	public static Double detailRatio = 0.5;   // detail panels. 
	
	public static Integer computeDownsample (Integer clientWidth) {
		// takes the width avaliable for plotting graphs and computes
		// a downsample value that would be used for requestData in GET 
		// methods.
		Integer criticalDownsampling = 1000;
		
		if ( clientWidth > criticalDownsampling) {
			return 1000;
		}
		
		return clientWidth;
	}
}
