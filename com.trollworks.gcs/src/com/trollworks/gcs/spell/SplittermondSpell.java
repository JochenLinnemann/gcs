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

package com.trollworks.gcs.spell;

import com.trollworks.gcs.character.GURPSCharacter;
import com.trollworks.gcs.datafile.DataFile;
import com.trollworks.gcs.datafile.LoadState;
import com.trollworks.gcs.skill.Skill;
import com.trollworks.gcs.skill.SkillDefault;
import com.trollworks.gcs.skill.SkillDefaultType;
import com.trollworks.gcs.skill.SkillDifficulty;
import com.trollworks.gcs.skill.SkillLevel;
import com.trollworks.gcs.skill.Technique;
import com.trollworks.gcs.template.Template;
import com.trollworks.gcs.ui.widget.outline.ListRow;
import com.trollworks.gcs.ui.widget.outline.RowEditor;
import com.trollworks.gcs.utility.I18n;
import com.trollworks.gcs.utility.SaveType;
import com.trollworks.gcs.utility.json.JsonMap;
import com.trollworks.gcs.utility.json.JsonWriter;

import java.io.IOException;
import java.util.Set;

public class SplittermondSpell extends RitualMagicSpell{
    /** The XML tag used for items. */
    public static final  String TAG_SPLITTERMOND_SPELL  = "splittermond_spell";
    private static final String TAG_NAME_DE             = "name_de";
    private              String mNameDe;

    /**
     * Creates a new Splittermond spell.
     *
     * @param dataFile The data file to associate it with.
     */
    public SplittermondSpell(DataFile dataFile) {
        super(dataFile);
        mPoints = 1;
        updateLevel(true);
    }

    /**
     * Creates a clone of an existing Splittermond spell and associates it with the specified data
     * file.
     *
     * @param dataFile          The data file to associate it with.
     * @param splittermondSpell The spell to clone.
     * @param deep              Whether or not to clone the children, grandchildren, etc.
     * @param forSheet          Whether this is for a character sheet or a list.
     */
    public SplittermondSpell(DataFile dataFile, SplittermondSpell splittermondSpell, boolean deep, boolean forSheet) {
        super(dataFile, splittermondSpell, deep, forSheet);
        mPoints = splittermondSpell.mPoints;
        updateLevel(true);
    }

    public SplittermondSpell(DataFile dataFile, JsonMap m, LoadState state) throws IOException {
        this(dataFile);
        load(m, state);
        updateLevel(true);
    }

    /**
     * Call to force an update of the level and relative level for this spell.
     *
     * @param notify Whether or not a notification should be issued on a change.
     */
    @Override
    public void updateLevel(boolean notify) {
        SkillLevel skillLevel = calculateLevel(getCharacter(), getName(), getBaseSkillName());

        if (mLevel == null || !mLevel.isSameLevelAs(skillLevel)) {
            mLevel = skillLevel;
            if (notify) {
                notify(ID_LEVEL, this);
            }
        }
    }

    /**
     * Calculates the spell level.
     *
     * @param character         The character the spell will be attached to.
     * @param name              The name of the spell.
     * @param baseSkillName     The base name of the skill the Splittermond Spell uses.
     * @return The calculated spell level.
     */
    public static SkillLevel calculateLevel(GURPSCharacter character, String name, String baseSkillName) {
        Skill              skill = character != null ? character.getBestSkillNamed(baseSkillName, null, false, null) : null;
        int                level = skill != null ? skill.getLevel() : Integer.MIN_VALUE;
        SkillLevel    skillLevel = new SkillLevel(level, 0, new StringBuilder());

        return skillLevel;
    }

    /**
     * Call to force an update of the points this spell.
     *
     * @param notify Whether or not a notification should be issued on a change.
     */
    public void updatePoints(boolean notify) {
        int prereqCount = getPrerequisiteSpellsCount();
        int points      = calculatePoints(prereqCount);

        if (mPoints != points) {
            mPoints = points;
            if (notify) {
                notify(ID_POINTS, this);
            }
        }
    }

    /**
     * Calculates the spell points.
     *
     * @param prereqCount The prerequisite count.
     * @return The calculated spell points.
     */
    public static int calculatePoints(int prereqCount) {
        return 1 + (int)Math.ceil(prereqCount / 2.0);
    }

    @Override
    public boolean setPrerequisiteSpellsCount(int prerequisiteSpellsCount) {
        if (super.setPrerequisiteSpellsCount(prerequisiteSpellsCount)) {
            updatePoints(true);

            return true;
        }

        return false;
    }

    @Override
    public String getLocalizedName() {
        return I18n.Text("Splittermond Spell");
    }

    @Override
    public String getJSONTypeName() {
        return TAG_SPLITTERMOND_SPELL;
    }

    @Override
    public String getRowType() {
        return I18n.Text("Splittermond Spell");
    }

    @Override
    protected void prepareForLoad(LoadState state) {
        super.prepareForLoad(state);
        mPoints = 1;
    }

    @Override
    protected void saveSelf(JsonWriter w, SaveType saveType) throws IOException {
        super.saveSelf(w, saveType);
        w.keyValue(TAG_NAME_DE, mNameDe);
    }

    @Override
    public RowEditor<? extends ListRow> createEditor() {
        return new SplittermondSpellEditor(this);
    }
}
