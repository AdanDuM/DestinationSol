package com.miloshpetrov.sol2.menu;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.miloshpetrov.sol2.Const;
import com.miloshpetrov.sol2.GameOptions;
import com.miloshpetrov.sol2.SolApplication;
import com.miloshpetrov.sol2.common.SolColor;
import com.miloshpetrov.sol2.game.item.SolItem;
import com.miloshpetrov.sol2.ui.*;

import java.util.ArrayList;
import java.util.List;

public class InputMapScreen implements SolUiScreen {
    public final InputMapKeyboardScreen inputMapKeyboardScreen;
    public final InputMapControllerScreen inputMapControllerScreen;
    public final InputMapMixedScreen inputMapMixedScreen;
    private final List<SolUiControl> controls;
    private final SolUiControl myPrevCtrl;
    public final SolUiControl nextCtrl;
    private final Rectangle myListArea;
    public final SolUiControl[] itemCtrls;
    private final Rectangle myDetailArea;
    private final Rectangle myItemCtrlArea;
    private final Vector2 myDetailHeaderPos;
    private final Rectangle myArea;
    public final SolUiControl backCtrl;
    private final SolUiControl upCtrl;
    private final SolUiControl downCtrl;


    private InputMapOperations myOperations;
    private int myPage;
    private List<SolItem> mySelected;
    private final Vector2 myListHeaderPos;
    public static final float SMALL_GAP = .004f;
    public static final float HEADER_TEXT_OFFS = .005f;
    private static final int BTN_ROWS = 4;


    public InputMapScreen(float r, GameOptions gameOptions) {
        controls = new ArrayList<SolUiControl>();

        float contentW = .8f;
        float col0 = r / 2 - contentW / 2;
        float row0 = .2f;
        float row = row0;
        float bgGap = MenuLayout.BG_BORDER;
        float bigGap = SMALL_GAP * 6;
        float headerH = .03f;


        // list header & controls
        myListHeaderPos = new Vector2(col0 + HEADER_TEXT_OFFS, row + HEADER_TEXT_OFFS); // offset hack
        float listCtrlW = contentW * .15f;
        Rectangle nextArea = new Rectangle(col0 + contentW - listCtrlW, row, listCtrlW, headerH);
        nextCtrl = new SolUiControl(nextArea, true, gameOptions.getKeyRight());
        nextCtrl.setDisplayName(">");
        controls.add(nextCtrl);
        Rectangle prevArea = new Rectangle(nextArea.x - SMALL_GAP - listCtrlW, row, listCtrlW, headerH);
        myPrevCtrl = new SolUiControl(prevArea, true, gameOptions.getKeyLeft());
        myPrevCtrl.setDisplayName("<");
        controls.add(myPrevCtrl);
        row += headerH + SMALL_GAP;

        // list
        float itemRowH = .04f;
        float listRow0 = row;
        itemCtrls = new SolUiControl[Const.ITEM_GROUPS_PER_PAGE];
        for (int i = 0; i < Const.ITEM_GROUPS_PER_PAGE; i++) {
            Rectangle itemR = new Rectangle(col0, row, contentW, itemRowH);
            SolUiControl itemCtrl = new SolUiControl(itemR, true);
            itemCtrls[i] = itemCtrl;
            controls.add(itemCtrl);
            row += itemRowH + SMALL_GAP;
        }
        myListArea = new Rectangle(col0, row, contentW, row - SMALL_GAP - listRow0);
        row += bigGap;

        // detail header & area
        myDetailHeaderPos = new Vector2(col0 + HEADER_TEXT_OFFS, row + HEADER_TEXT_OFFS); // offset hack
        row += headerH + SMALL_GAP;
        float itemCtrlAreaW = contentW * .4f;
        myItemCtrlArea = new Rectangle(col0 + contentW - itemCtrlAreaW, row, itemCtrlAreaW, .2f);
        myDetailArea = new Rectangle(col0, row, contentW - itemCtrlAreaW - SMALL_GAP, myItemCtrlArea.height);
        row += myDetailArea.height;

        // whole
        myArea = new Rectangle(col0 - bgGap, row0 - bgGap, contentW + bgGap * 2, row - row0 + bgGap * 2);

        backCtrl = new SolUiControl(itemCtrl(3), true, gameOptions.getKeyClose());
        backCtrl.setDisplayName("Back");
        controls.add(backCtrl);

        upCtrl = new SolUiControl(null, true, gameOptions.getKeyUp());
        controls.add(upCtrl);
        downCtrl = new SolUiControl(null, true, gameOptions.getKeyDown());
        controls.add(downCtrl);

        inputMapKeyboardScreen = new InputMapKeyboardScreen(this, gameOptions);
        inputMapControllerScreen = new InputMapControllerScreen(this, gameOptions);
        inputMapMixedScreen = new InputMapMixedScreen(this, gameOptions);
    }

    @Override
    public List<SolUiControl> getControls() {
        return controls;
    }

    @Override
    public void updateCustom(SolApplication cmp, SolInputManager.Ptr[] ptrs, boolean clickedOutside) {
        SolInputManager im = cmp.getInputMan();
        MenuScreens screens = cmp.getMenuScreens();

        if (backCtrl.isJustOff()) {
            im.setScreen(cmp, screens.options);
        }

    }

    @Override
    public void drawBg(UiDrawer uiDrawer, SolApplication cmp) {

    }

    @Override
    public void drawImgs(UiDrawer uiDrawer, SolApplication cmp) {

    }

    @Override
    public void drawText(UiDrawer uiDrawer, SolApplication cmp) {
        uiDrawer.drawString(myOperations.getHeader(), myListHeaderPos.x, myListHeaderPos.y, FontSize.WINDOW, false, SolColor.W);
    }

    @Override
    public boolean reactsToClickOutside() {
        return false;
    }

    @Override
    public boolean isCursorOnBg(SolInputManager.Ptr ptr) {
        return false;
    }

    @Override
    public void onAdd(SolApplication cmp) {

    }

    @Override
    public void blurCustom(SolApplication cmp) {

    }

    public Rectangle itemCtrl(int row) {
        float h = (myItemCtrlArea.height - SMALL_GAP * (BTN_ROWS - 1)) / BTN_ROWS;
        return new Rectangle(myItemCtrlArea.x, myItemCtrlArea.y + (h + SMALL_GAP) * row, myItemCtrlArea.width, h);
    }

    public void setOperations(InputMapOperations operations) {
        myOperations = operations;
    }

}
