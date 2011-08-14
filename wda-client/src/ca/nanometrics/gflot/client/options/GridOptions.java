/*
 * Copyright (c) 2008 Nanometrics Inc. 
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package ca.nanometrics.gflot.client.options;

import ca.nanometrics.gflot.client.util.JSONObjectWrapper;

/**
 * @author Alexander De Leon
 */
public class GridOptions extends JSONObjectWrapper {

	public GridOptions setColor(String color) {
		put("color", color);
		return this;
	}

	public GridOptions setBackgroundColor(String color) {
		put("backgroundColor", color);
		return this;
	}

	public GridOptions setTickColor(String color) {
		put("tickColor", color);
		return this;
	}

	public GridOptions setLabelMargin(int labelMargin) {
		put("labelMargin", new Integer(labelMargin));
		return this;
	}

	public GridOptions setMarkings(Markings markings) {
		put("markings", markings);
		return this;
	}

	public GridOptions setBorderWidth(int borderWidth) {
		put("borderWidth", new Integer(borderWidth));
		return this;
	}

	public GridOptions setClickable(boolean clickable) {
		put("clickable", clickable);
		return this;
	}

	public GridOptions setHoverable(boolean hoverable) {
		put("hoverable", hoverable);
		return this;
	}

	public GridOptions setAutoHighlight(boolean autoHighlight) {
		put("autoHighlight", autoHighlight);
		return this;
	}

	public GridOptions setMouseActiveRadius(int mouseActiveRadius) {
		put("mouseActiveRadius", new Integer(mouseActiveRadius));
		return this;
	}

}
