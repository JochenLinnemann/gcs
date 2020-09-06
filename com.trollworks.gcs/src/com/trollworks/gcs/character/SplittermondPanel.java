/*
 * Copyright ©2020 by Jochen Linnemann. All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, version 2.0. If a copy of the MPL was not distributed with
 * this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, version 2.0.
 */

package com.trollworks.gcs.character;

import com.trollworks.gcs.page.DropPanel;
import com.trollworks.gcs.page.PageField;
import com.trollworks.gcs.page.PageLabel;
import com.trollworks.gcs.page.PagePoints;
import com.trollworks.gcs.ui.layout.ColumnLayout;
import com.trollworks.gcs.ui.layout.PrecisionLayoutAlignment;
import com.trollworks.gcs.ui.layout.PrecisionLayoutData;
import com.trollworks.gcs.ui.widget.Wrapper;
import com.trollworks.gcs.utility.I18n;

import java.awt.Color;
import java.awt.Container;
import javax.swing.SwingConstants;

/** The Splittermond panel. */
public class SplittermondPanel extends DropPanel {
    /**
     * Creates a new Splittermond panel.
     *
     * @param sheet The sheet to display the data for.
     */
    public SplittermondPanel(CharacterSheet sheet) {
        super(new ColumnLayout(5, 2, 0), I18n.Text("Splintermoon"));

        Wrapper wrapper = new Wrapper(new ColumnLayout(2, 2, 0));
        createLabelAndField(wrapper, sheet, Profile.ID_RACE, I18n.Text("Race:"), null);
        createLabelAndField(wrapper, sheet, Profile.ID_CULTURE, I18n.Text("Culture:"), null);
        createLabelAndField(wrapper, sheet, Profile.ID_ORIGIN, I18n.Text("Origin:"), null);
        createLabelAndField(wrapper, sheet, Profile.ID_TRAINING, I18n.Text("Training:"), null);
        add(wrapper);

        createDivider();

        wrapper = new Wrapper(new ColumnLayout(3, 2, 0));
        addLabelAndField(wrapper, sheet, Profile.ID_MOONSIGN, I18n.Text("Moonsign"), null, true);
        addLabelAndField(wrapper, sheet, GURPSCharacter.ID_SPLINTER_POINTS, I18n.Text("Splinter Points"), I18n.Text("Normal (i.e. fully restored) splinter points"), true);
        addLabelAndField(wrapper, sheet, GURPSCharacter.ID_CURRENT_SPLINTER_POINTS, I18n.Text("Current"), I18n.Text("Current splinter points"), true);
        add(wrapper);

        createDivider();

        wrapper = new Wrapper(new ColumnLayout(3, 2, 0));
        addLabelAndField(wrapper, sheet, GURPSCharacter.ID_MYSTICISM, I18n.Text("Mysticism"), null, true);
        addLabelAndField(wrapper, sheet, GURPSCharacter.ID_FOCUS_POINTS, I18n.Text("Focus Points"), I18n.Text("Normal (i.e. fully rested) focus points"), true);
        addLabelAndField(wrapper, sheet, GURPSCharacter.ID_CURRENT_FOCUS, I18n.Text("Current"), I18n.Text("Current focus points"), true);
        add(wrapper);
    }

    private void addLabelAndField(Container parent, CharacterSheet sheet, String key, String title, String tooltip, boolean enabled) {
        parent.add(new PagePoints(sheet, key), new PrecisionLayoutData().setHorizontalAlignment(PrecisionLayoutAlignment.END));
        PageField field = new PageField(sheet, key, SwingConstants.RIGHT, enabled, tooltip);
        parent.add(field, new PrecisionLayoutData().setGrabHorizontalSpace(true).setHorizontalAlignment(PrecisionLayoutAlignment.FILL));
        parent.add(new PageLabel(title, field));
    }

    private void createDivider() {
        Wrapper panel = new Wrapper();
        panel.setOnlySize(1, 1);
        add(panel);
        addVerticalBackground(panel, Color.black);
    }
}
