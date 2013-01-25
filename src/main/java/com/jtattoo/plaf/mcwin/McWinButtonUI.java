/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*  
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*  
* see: gpl-2.0.txt
* 
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
* 
* see: lgpl-2.0.txt
* see: classpath-exception.txt
* 
* Registered users could also use JTattoo under the terms and conditions of the 
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*  
* see: APACHE-LICENSE-2.0.txt
*/
 
package com.jtattoo.plaf.mcwin;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * @author Michael Hagen
 */
public class McWinButtonUI extends BaseButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new McWinButtonUI();
    }

    protected void paintBackground(Graphics g, AbstractButton b) {
        if (b.getParent() instanceof JToolBar) {
            b.setContentAreaFilled(true);
        }
        if (!b.isContentAreaFilled() || (b.getParent() instanceof JMenuBar)) {
            return;
        }

        int width = b.getWidth();
        int height = b.getHeight();

        if (!(b.isBorderPainted() && (b.getBorder() instanceof UIResource))
                || (b.getParent() instanceof JToolBar)) {
            super.paintBackground(g, b);
            if ((b.getParent() instanceof JToolBar)) {
                g.setColor(Color.lightGray);
                g.drawRect(0, 0, width - 2, height - 1);
                g.setColor(AbstractLookAndFeel.getTheme().getToolbarBackgroundColor());
                g.drawLine(width - 1, 0, width - 1, height - 1);
            }
            return;
        }

        ButtonModel model = b.getModel();
        Graphics2D g2D = (Graphics2D) g;
        Composite composite = g2D.getComposite();
        Object savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()
                || (((width < 64) || (height < 16)) && ((b.getText() == null) || b.getText().length() == 0))) {
            Color[] backColors = null;
            if (b.getBackground() instanceof ColorUIResource) {
                if (!model.isEnabled()) {
                    backColors = AbstractLookAndFeel.getTheme().getDisabledColors();
                } else if (model.isPressed() && model.isArmed()) {
                    backColors = new Color[] { AbstractLookAndFeel.getTheme().getBackgroundColor() };
                } else if (b.isRolloverEnabled() && model.isRollover()) {
                    backColors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else if (b.equals(b.getRootPane().getDefaultButton())) {
                    if (JTattooUtilities.isFrameActive(b)) {
                        if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
                            backColors = AbstractLookAndFeel.getTheme().getFocusColors();
                        } else {
                            if (AbstractLookAndFeel.getTheme().isBrightMode()) {
                                backColors = new Color[AbstractLookAndFeel.getTheme().getSelectedColors().length];
                                for (int i = 0; i < backColors.length; i++) {
                                    backColors[i] = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getSelectedColors()[i], 30);
                                }
                            } else {
                                backColors = AbstractLookAndFeel.getTheme().getSelectedColors();
                            }
                        }
                    } else {
                        backColors = AbstractLookAndFeel.getTheme().getButtonColors();
                    }
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && b.hasFocus()) {
                        backColors = AbstractLookAndFeel.getTheme().getFocusColors();
                    } else {
                        backColors = AbstractLookAndFeel.getTheme().getButtonColors();
                    }
                }
            } else {
                backColors = ColorHelper.createColorArr(ColorHelper.brighter(b.getBackground(), 20), ColorHelper.darker(b.getBackground(), 20), 20);
            }
            JTattooUtilities.fillHorGradient(g, backColors, 0, 0, width - 1, height - 1);
            Color frameColor = backColors[backColors.length / 2];
            g2D.setColor(ColorHelper.darker(frameColor, 25));
            g2D.drawRect(0, 0, width - 1, height - 1);
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawRect(1, 1, width - 3, height - 3);
        } else if (model.isPressed() && model.isArmed()) {
            int d = height - 2;
            Color color = AbstractLookAndFeel.getTheme().getBackgroundColor();
            g2D.setColor(color);
            g2D.fillRoundRect(1, 1, width - 1, height - 1, d, d);
            g2D.setColor(ColorHelper.darker(color, 40));
            g2D.drawRoundRect(0, 0, width - 1, height - 1, d, d);
        } else {
            int d = height - 2;
            Color[] backColors = null;
            if (b.getBackground() instanceof ColorUIResource) {
                if (!model.isEnabled()) {
                    backColors = AbstractLookAndFeel.getTheme().getDisabledColors();
                } else if (b.isRolloverEnabled() && model.isRollover()) {
                    backColors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else if (b.equals(b.getRootPane().getDefaultButton())) {
                    if (JTattooUtilities.isFrameActive(b)) {
                        backColors = AbstractLookAndFeel.getTheme().getSelectedColors();
                    } else {
                        backColors = AbstractLookAndFeel.getTheme().getButtonColors();
                    }
                } else {
                    backColors = AbstractLookAndFeel.getTheme().getButtonColors();
                }
            } else {
                backColors = ColorHelper.createColorArr(ColorHelper.brighter(b.getBackground(), 20), ColorHelper.darker(b.getBackground(), 20), 20);
            }
            Color frameColor = backColors[backColors.length / 2];

            Shape savedClip = g.getClip();
            Area clipArea = new Area(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, d, d));
            clipArea.intersect(new Area(savedClip));
            g2D.setClip(clipArea);
            JTattooUtilities.fillHorGradient(g, backColors, 0, 0, width - 1, height - 1);
            g2D.setClip(savedClip);

            g2D.setColor(ColorHelper.darker(frameColor, 25));
            g2D.drawRoundRect(0, 0, width - 1, height - 1, d, d);

            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawRoundRect(1, 1, width - 3, height - 3, d - 2, d - 2);

        }
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRenderingHint);
        g2D.setComposite(composite);
    }

    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        Graphics2D g2D = (Graphics2D) g;
        int width = b.getWidth();
        int height = b.getHeight();
        if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()
                || !b.isContentAreaFilled()
                || ((width < 64) || (height < 16)) && ((b.getText() == null) || b.getText().length() == 0)) {
            g.setColor(AbstractLookAndFeel.getFocusColor());
            BasicGraphicsUtils.drawDashedRect(g, 4, 3, width - 8, height - 6);
        } else {
            Object savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.setColor(AbstractLookAndFeel.getFocusColor());
            int d = b.getHeight() - 4;
            g2D.drawRoundRect(2, 2, b.getWidth() - 5, b.getHeight() - 5, d, d);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRenderingHint);
        }
    }

}


