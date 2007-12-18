package ca.shu.ui.lib.objects;

import ca.shu.ui.lib.Style.Style;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * Text Node
 * 
 * @author Shu Wu
 */
public class PXText extends PText {

	private static final long serialVersionUID = 1L;

	public PXText() {
		super();
		init();
	}

	public PXText(String aText) {
		super(aText);
		init();
	}

	private void init() {
		setGreekThreshold(4);
		setConstrainWidthToTextWidth(true);

		setFont(Style.FONT_NORMAL);
		setTextPaint(Style.COLOR_FOREGROUND);
	}
}