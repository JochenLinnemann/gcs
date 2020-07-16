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
import com.trollworks.gcs.ui.layout.ColumnLayout;
import com.trollworks.gcs.ui.widget.Wrapper;
import com.trollworks.gcs.utility.I18n;

import java.awt.Color;
import javax.swing.SwingConstants;

/** The Splittermond panel. */
public class SplittermondPanel extends DropPanel {
    /**
     * Creates a new Splittermond panel.
     *
     * @param sheet The sheet to display the data for.
     */
    public SplittermondPanel(CharacterSheet sheet) {
        super(new ColumnLayout(5, 2, 0), I18n.Text("Splittermond"));

        Wrapper wrapper = new Wrapper(new ColumnLayout(2, 2, 0));
        createLabelAndField(wrapper, sheet, Profile.ID_RACE, I18n.Text("Rasse:"), null, SwingConstants.LEFT);
        createLabelAndField(wrapper, sheet, Profile.ID_CULTURE, I18n.Text("Kultur:"), null, SwingConstants.LEFT);
        add(wrapper);

        createDivider();

        wrapper = new Wrapper(new ColumnLayout(2, 2, 0));
        createLabelAndField(wrapper, sheet, Profile.ID_ORIGIN, I18n.Text("Abstammung:"), null, SwingConstants.LEFT);
        createLabelAndField(wrapper, sheet, Profile.ID_TRAINING, I18n.Text("Ausbildung:"), null, SwingConstants.LEFT);
        add(wrapper);

        createDivider();

        wrapper = new Wrapper(new ColumnLayout(2, 2, 0));
        createLabelAndField(wrapper, sheet, Profile.ID_MOONSIGN, I18n.Text("Mondzeichen:"), null, SwingConstants.LEFT);
        createLabelAndField(wrapper, sheet, Profile.ID_SPLINTERPOINTS, I18n.Text("Splitterpunkte:"), null, SwingConstants.LEFT);
        add(wrapper);
    }

    private void createDivider() {
        Wrapper panel = new Wrapper();
        panel.setOnlySize(1, 1);
        add(panel);
        addVerticalBackground(panel, Color.black);
    }
}
