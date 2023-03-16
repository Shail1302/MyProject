    private class UndirListPickActions extends CWidgetActionAdapter
    {
        public boolean onFormEntry(final IForm _frm) throws Exception {
            AFormLogic newFrm = null;
            MocaResults rs = UndirListPick.this.session.getGlobalMocaResults();
            int retStatus = 0;
            UndirListPick.this.starterPalletFlag = false;
            if ("1".equals(UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.pck_available_flg"))) {
                try {
                    if (CWmdMtfUtil.isMorePickWorkToPerform(UndirListPick.this.display, UndirListPick.this.display.getVariable("global.wh_id"), UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_grp_id"))) {
                        final MocaResults lstRfPckRes = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.display.getVariable("global.wh_id"), UndirListPick.this.display.getVariable("global.devcod"), UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_grp_id")));
                        if (lstRfPckRes.getRowCount() > 0) {
                            UndirListPick.this.display.setVariable("INIT_POLICIES.prvfrm", "UNDIR_LIST_PICK");
                            newFrm = UndirListPick.this.display.createFormLogic("LOCATION_DISPLAY", MtfConstants.EFlow.SHOW_FORM);
                            newFrm.run();
                            UndirListPick.this.display.createFormLogic("PICK_LIST_DISPLAY", MtfConstants.EFlow.SHOW_FORM).run();
                        }
                    }
                }
                catch (MocaException ex) {}
            }
            UndirListPick.this.efPrinter.setEnabled(false);
            UndirListPick.this.efPrinter.setVisible(false);
            if (UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("DEPOSIT_A") && UndirListPick.this.wmdMtf.partialPck(UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_grp_id"), UndirListPick.this.whId) && !UndirListPick.this.efFromLoadPickup.getText().equals("1")) {
                UndirListPick.this.completeList();
            }
            else if (UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("DIR_LIST_PICK") && CWmdMtfUtil.checkListForSequencedOrder(UndirListPick.this.display, UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_grp_id"))) {
                UndirListPick.this.efListId.clear();
                UndirListPick.this.efListCnt.clear();
                UndirListPick.this.efListIdList.clear();
                UndirListPick.this.display.clearField("UNDIR_LIST_PICK.list_grp_id");
                UndirListPick.this.efAssetPickedFlg.setText("0");
            }
            try {
                if (UndirListPick.this.efListGrpId.getText().length() == 0 && !UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("DEPOSIT_A")) {
                    rs = UndirListPick.this.session.executeDSQL("generate next number     where numcod = 'list_grp_id' ");
                    if (rs != null && rs.next()) {
                        UndirListPick.this.efListGrpId.setText(rs.getString("nxtnum"));
                    }
                    UndirListPick.this.cmdFkeyDone.setEnabled(false);
                }
                else {
                    UndirListPick.this.cmdFkeyDone.setEnabled(true);
                }
            }
            catch (MocaException e) {
                UndirListPick.this.display.beep();
                UndirListPick.this.frmMain.displayErrorMessage();
                return false;
            }
            if (UndirListPick.this.display.getVariable("DEPOSIT_A.partial_pick").equals("1")) {
                try {
                    if (UndirListPick.this.efListGrpId.getText().length() == 0 && UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("DEPOSIT_A")) {
                        rs = UndirListPick.this.session.executeDSQL("generate next number     where numcod = 'list_grp_id' ");
                        if (rs != null && rs.next()) {
                            UndirListPick.this.efListGrpId.setText(rs.getString("nxtnum"));
                        }
                        UndirListPick.this.cmdFkeyDone.setEnabled(false);
                    }
                    else {
                        UndirListPick.this.cmdFkeyDone.setEnabled(true);
                    }
                }
                catch (MocaException e) {
                    UndirListPick.this.display.beep();
                    UndirListPick.this.frmMain.displayErrorMessage();
                    return false;
                }
            }
            UndirListPick.this.display.setVariable("INIT_POLICIES.active_actcod", "LSTPCK");
            if (UndirListPick.this.display.getVariable("global.directed_mode").equals("Y")) {
                UndirListPick.this.efListId.setText(UndirListPick.this.display.getVariable("DIR_LIST_PICK.list_id"));
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.display.getVariable("DIR_LIST_PICK.list_id")));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (!UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("DEPOSIT_A") && retStatus == 0) {
                    try {
                        rs = null;
                        retStatus = 0;
                        rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.display.getVariable("global.wh_id"), UndirListPick.this.efListId.getText(), UndirListPick.this.efListGrpId.getText()));
                    }
                    catch (MocaException e) {
                        retStatus = e.getErrorCode();
                    }
                    if (retStatus != 0) {
                        UndirListPick.this.display.beep();
                        UndirListPick.this.frmMain.displayErrorMessage();
                    }
                    UndirListPick.this.efListSts.setText(UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_sts"));
                    UndirListPick.this.enablePrinter();
                }
            }
            if (!UndirListPick.this.wmdMtf.getWrkQue().getOprCod().equals("RLPCK")) {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListGrpId.getText(), UndirListPick.this.whId, UndirListPick.this.devCode));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (rs != null && retStatus == 0) {
                    String next_list_id = "";
                    if (rs.next()) {
                        next_list_id = rs.getString("list_id");
                    }
                    if (!next_list_id.equals(UndirListPick.this.efListId.getText())) {
                        UndirListPick.this.efToId.setText("");
                    }
                }
            }
            if (UndirListPick.this.efToId.getText().length() > 0) {
                UndirListPick.log.trace(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText(), UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.to_id")));
                UndirListPick.this.wmdMtf.setToIdMap(UndirListPick.this.efListId.getText(), UndirListPick.this.efToId.getText());
            }
            Label_1450: {
                if (UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_grp_id").length() > 0) {
                    try {
                        rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListGrpId.getText()));
                        rs.next();
                        UndirListPick.this.efListCnt.setText(Integer.toString(rs.getInt("listCount")));
                        if (UndirListPick.this.efListCnt.getText().equals("0")) {
                            UndirListPick.this.cmdFkeyDone.setEnabled(false);
                        }
                        break Label_1450;
                    }
                    catch (MocaException e) {
                        UndirListPick.this.display.beep();
                        UndirListPick.this.frmMain.displayErrorMessage();
                        return false;
                    }
                }
                UndirListPick.this.efListCnt.setText("0");
                UndirListPick.this.cmdFkeyDone.setEnabled(false);
            }
            if (UndirListPick.this.partialPckCarton() && UndirListPick.this.pckInPndLoc()) {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText(), UndirListPick.this.efListGrpId.getText(), UndirListPick.this.display.getVariable("global.wh_id")));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
            }
            if (UndirListPick.this.efAssettyp.getText().length() > 0 && UndirListPick.this.efWrklstId.getText().length() > 0 && UndirListPick.this.efAssetPickedFlg.getText().equals("1") && UndirListPick.this.efToId.getText().length() == 0 && UndirListPick.this.isAssetCategoryEnabledForType(UndirListPick.this.efAssettyp.getText())) {
                UndirListPick.this.efToId.setEnabled(false);
                UndirListPick.this.efToId.setVisible(false);
                UndirListPick.this.efToId.setEntryRequired(false);
                UndirListPick.this.efPosId.setVisible(false);
                UndirListPick.this.efPosId.setEnabled(false);
            }
            if (UndirListPick.this.efAssettyp.getText().length() > 0 && UndirListPick.this.efWrklstId.getText().length() > 0 && UndirListPick.this.efAssetPickedFlg.getText().equals("0") && UndirListPick.this.isAssetCategoryEnabledForType(UndirListPick.this.efAssettyp.getText())) {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efWrklstId.getText(), UndirListPick.this.display.getVariable("global.wh_id")));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (retStatus == -1403) {
                    UndirListPick.this.clearAssetListPickingFields();
                    UndirListPick.this.efAssetPickedFlg.setText("0");
                    UndirListPick.this.display.clearField("ASSET_IDENTIFY.asset_typ");
                    UndirListPick.this.display.clearField("ASSET_IDENTIFY.wrklst_id");
                    UndirListPick.this.display.clearField("ASSET_IDENTIFY.wrkref");
                }
                if (retStatus == 0 && !UndirListPick.this.pckInPndLoc() && !UndirListPick.this.partialPckCarton() && UndirListPick.this.efAssetPickedFlg.getText().equals("0")) {
                    UndirListPick.this.display.setVariable("INIT_POLICIES.prvfrm", "UNDIR_LIST_PICK");
                    UndirListPick.this.display.setVariable("ASSET_IDENTIFY.asset_typ", UndirListPick.this.efAssettyp.getText());
                    UndirListPick.this.display.setVariable("ASSET_IDENTIFY.wrklst_id", UndirListPick.this.efWrklstId.getText());
                    UndirListPick.this.display.setVariable("ASSET_IDENTIFY.wrkref", UndirListPick.this.efWrkref.getText());
                    if (UndirListPick.this.isPickListSlot(UndirListPick.this.efWrklstId.getText())) {
                        UndirListPick.this.display.setVariable("ASSET_IDENTIFY.slot_asset_iden_flg", "0");
                    }
                    UndirListPick.this.display.setVariable("ASSET_IDENTIFY.mode", "AA");
                    newFrm = UndirListPick.this.display.createFormLogic("ASSET_IDENTIFY", MtfConstants.EFlow.SHOW_FORM);
                    newFrm.run();
                    if (UndirListPick.this.display.getVariable("ASSET_IDENTIFY.pick_to_id").equals("") && UndirListPick.this.display.getVariable("ASSET_IDENTIFY.pickToIdFlg").equals("0") && !UndirListPick.this.display.getVariable("ASSET_IDENTIFY.valStarterPalFlg").equals("1")) {
                        UndirListPick.this.clearAssetListPickingFields();
                        UndirListPick.this.processBack();
                    }
                    UndirListPick.this.efAssetPickedFlg.setText("1");
                    UndirListPick.this.efToId.setEnabled(false);
                    UndirListPick.this.display.setVariable("PICKUP_A.assetPickedFlg", UndirListPick.this.efAssetPickedFlg.getText());
                    UndirListPick.this.display.setVariable("PICKUP_A.dst_asset_typ", UndirListPick.this.efAssettyp.getText());
                    UndirListPick.this.display.setVariable("PICKUP_B.dst_asset_typ", UndirListPick.this.efAssettyp.getText());
                    for (int i = 1; i <= 5; ++i) {
                        final String temp = invokedynamic(makeConcatWithConstants:(I)Ljava/lang/String;, i);
                        if (CMtfUtil.verify(UndirListPick.this.display.getVariable(temp), "Y") == 1) {
                            UndirListPick.this.display.setVariable(temp, "1");
                        }
                        else {
                            UndirListPick.this.display.setVariable(temp, "0");
                        }
                    }
                    UndirListPick.this.display.setVariable("PICKUP_A.load_attr1_flg", UndirListPick.this.display.getVariable("ASSET_IDENTIFY.load_attr1_flg"));
                    UndirListPick.this.display.setVariable("PICKUP_A.load_attr2_flg", UndirListPick.this.display.getVariable("ASSET_IDENTIFY.load_attr2_flg"));
                    UndirListPick.this.display.setVariable("PICKUP_A.load_attr3_flg", UndirListPick.this.display.getVariable("ASSET_IDENTIFY.load_attr3_flg"));
                    UndirListPick.this.display.setVariable("PICKUP_A.load_attr4_flg", UndirListPick.this.display.getVariable("ASSET_IDENTIFY.load_attr4_flg"));
                    UndirListPick.this.display.setVariable("PICKUP_A.load_attr5_flg", UndirListPick.this.display.getVariable("ASSET_IDENTIFY.load_attr5_flg"));
                    if (UndirListPick.this.display.getVariable("ASSET_IDENTIFY.ser_flg").equals("1") && UndirListPick.this.display.getVariable("ASSET_IDENTIFY.partialPckAssetFlg").equals("0")) {
                        UndirListPick.this.display.setVariable("PICKUP_A.asset_id", UndirListPick.this.efAssetId.getText());
                        UndirListPick.this.display.setVariable("PICKUP_B.asset_id", UndirListPick.this.efAssetId.getText());
                    }
                    if (UndirListPick.this.efAssettyp.toString().length() > 1) {
                        UndirListPick.this.display.setVariable("PICKUP_B.list_id", UndirListPick.this.efWrklstId.getText());
                        UndirListPick.this.efAssetId.clear();
                        UndirListPick.this.display.clearField("ASSET_IDENTIFY.asset_typ");
                        UndirListPick.this.display.clearField("ASSET_IDENTIFY.cnfrm_asset_typ");
                        UndirListPick.this.display.clearField("ASSET_IDENTIFY.pick_to_id");
                        UndirListPick.this.display.getVariable("ASSET_IDENTIFY.pickToIdFlg");
                    }
                    UndirListPick.this.display.clearField("ASSET_IDENTIFY.slot_asset_iden_flg");
                    UndirListPick.this.assetIdentifyForSlot();
                }
            }
            else if (UndirListPick.this.efMultiAssetListFlag.getText() != null && UndirListPick.this.efMultiAssetListFlag.getText().equals("1")) {
                UndirListPick.this.display.setVariable("INIT_POLICIES.prvfrm", "UNDIR_LIST_PICK");
                if (!UndirListPick.this.wmdMtf.checkMultiAssetListSlotChange(UndirListPick.this.display, UndirListPick.this.frmMain, UndirListPick.this.efListId.getText(), UndirListPick.this.efWrkref.getText())) {
                    UndirListPick.this.clearAssetListPickingFields();
                    UndirListPick.this.processBack();
                }
                UndirListPick.this.display.setVariable("INIT_POLICIES.prvfrm", "UNDIR_LIST_PICK");
            }
            else if (UndirListPick.this.efAssettyp.getText().length() > 0 && UndirListPick.this.efWrklstId.getText().length() > 0 && UndirListPick.this.efAssetPickedFlg.getText().equals("1") && UndirListPick.this.isAssetCategoryEnabledForType(UndirListPick.this.efAssettyp.getText())) {
                UndirListPick.this.assetIdentifyForSlot();
            }
            if (!UndirListPick.this.efPckmod.getText().equals("1") && UndirListPick.this.efAssetPickedFlg.getText().equals("0")) {
                UndirListPick.this.efPckmod.setText("0");
                UndirListPick.this.efWrklstId.setEnabled(true);
            }
            UndirListPick.this.efPalletId.clear();
            UndirListPick.this.efPalletLoc.clear();
            UndirListPick.this.efPalletctlexpflg.clear();
            UndirListPick.this.efPckerrflg.clear();
            if (UndirListPick.this.efToId.getText().length() == 0) {
                UndirListPick.this.efToId.setEnabled(true);
                UndirListPick.this.efToId.setVisible(true);
                UndirListPick.this.efPosId.setVisible(true);
                UndirListPick.this.efPosId.setEnabled(true);
            }
            if (CMtfUtil.verify(UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm"), "DEPOSIT_A") == 1) {
                UndirListPick.this.efToId.clear();
                UndirListPick.this.efPosId.clear();
            }
            if (UndirListPick.this.efPckCode.getText().equals("")) {
                UndirListPick.this.efPckCode.setText("0");
            }
            if (UndirListPick.this.efPckmod.getText().trim().equals("1") && UndirListPick.this.efPckCode.getText().equals("0")) {
                UndirListPick.this.processFormExecution();
            }
            else {
                if (CMtfUtil.verify(UndirListPick.this.display.getVariable("global.directed_mode"), "Y") == 1) {
                    UndirListPick.this.efBackForm.setText("LOOK_WORK");
                    try {
                        rs = null;
                        retStatus = 0;
                        rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText(), UndirListPick.this.efMultiAssetListFlag.getText()));
                    }
                    catch (MocaException e) {
                        retStatus = e.getErrorCode();
                    }
                    if (retStatus == 0) {
                        if (rs != null && retStatus == 0) {
                            retStatus = CMtfUtil.getResults(UndirListPick.this.display);
                            rs = UndirListPick.this.session.getGlobalMocaResults();
                            UndirListPick.this.efWrkref.setText(CMtfUtil.getValue(rs, "wrkref"));
                            UndirListPick.this.efWrktyp.setText(CMtfUtil.getValue(rs, "wrktyp"));
                            UndirListPick.this.efSubnum.setText(CMtfUtil.getValue(rs, "subnum"));
                            UndirListPick.this.starterPalletFlag = rs.getBoolean("start_pal_flg");
                            UndirListPick.this.enablePositionID();
                        }
                        if (CMtfUtil.verify(CMtfUtil.getValue(rs, "start_pal_flg"), "1") == 1) {
                            UndirListPick.this.efToId.setVisible(false);
                            UndirListPick.this.efToId.setEnabled(false);
                            UndirListPick.this.efToId.setEntryRequired(false);
                            UndirListPick.this.efPosId.setVisible(false);
                            UndirListPick.this.efPosId.setEnabled(false);
                        }
                    }
                    else {
                        UndirListPick.this.efPckmod.setText("0");
                        UndirListPick.this.efWrklstId.setEnabled(true);
                        UndirListPick.this.efWrklstId.clear();
                        UndirListPick.this.efListId.clear();
                        UndirListPick.this.efListIdList.clear();
                        UndirListPick.this.efWrkref.clear();
                        UndirListPick.this.efToId.clear();
                        UndirListPick.this.efPosId.clear();
                        UndirListPick.this.efClusterListPckFlg.clear();
                        UndirListPick.this.efListGrpId.clear();
                        UndirListPick.this.custOrderNotes(false);
                        newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                        newFrm.run();
                    }
                    UndirListPick.this.efWrklstId.setVisible(false);
                    UndirListPick.this.efClientId.setVisible(false);
                    UndirListPick.this.efWkorev.setVisible(false);
                }
                else if (!UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("MASTER_LIST_INFO")) {
                    UndirListPick.this.efBackForm.setText("UNDIR_LIST_PICK");
                    if (UndirListPick.this.display.getPreviousFormOnStack().getIdentifier().equals("LOAD_PICKUP")) {
                        UndirListPick.this.efWrklstId.setEnabled(true);
                        UndirListPick.this.efWrklstId.setVisible(true);
                        UndirListPick.this.efWrklstId.setEntryRequired(true);
                        UndirListPick.this.efWrklstId.setText(UndirListPick.this.efWrklstId.getText().trim());
                        UndirListPick.this.efWrklstId.clear();
                        UndirListPick.this.efListId.clear();
                        UndirListPick.this.efToId.clear();
                        UndirListPick.this.efPosId.clear();
                        UndirListPick.this.efClientId.setVisible(false);
                        UndirListPick.this.efWkorev.setVisible(false);
                    }
                }
                if (UndirListPick.this.display.getVariable("INIT_POLICIES.prvfrm").equals("MASTER_LIST_INFO")) {
                    if (UndirListPick.this.efWrklstId.getText().length() > 0) {
                        UndirListPick.this.efToId.setFocus();
                    }
                    if (UndirListPick.this.efToId.getText().length() > 0) {
                        UndirListPick.this.efPosId.setFocus();
                    }
                    if (UndirListPick.this.efPosId.getText().length() > 0) {
                        UndirListPick.this.efPosId.setFocus();
                    }
                }
            }
            if (UndirListPick.this.display.getVariable("global.directed_mode").equals("Y")) {
                if (COrderNotesDsp.checkOrderNotConfig(UndirListPick.this.frmMain, UndirListPick.this.display, UndirListPick.this.session, UndirListPick.this.exitpnt_lst, UndirListPick.this.efWrklstId.getText(), "")) {
                    UndirListPick.this.custOrderNotes(true);
                }
                else {
                    UndirListPick.this.custOrderNotes(false);
                }
            }
            if (UndirListPick.this.efWrklstId.getText().length() > 0 && UndirListPick.this.isPickListSlot(UndirListPick.this.efWrklstId.getText())) {
                UndirListPick.this.efPosId.setVisible(false);
            }
            return true;
        }
        
        public boolean onFormExit(final IForm _frm) throws Exception {
            AFormLogic newFrm = null;
            MocaResults rs = UndirListPick.this.session.getGlobalMocaResults();
            int retStatus = 0;
            String startPalletControl = null;
            String wrkRef = null;
            if (UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_id") != null && UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_id").trim().length() > 0 && CWmdMtfUtil.isPickListCloseForSequenceOrderBeforePick(UndirListPick.this.display, UndirListPick.this.display.getVariable("UNDIR_LIST_PICK.list_id"))) {
                UndirListPick.this.display.beep();
                UndirListPick.this.frmMain.promptMessageAnyKey("errNoListForWorkListID");
                UndirListPick.this.efPckmod.setText("0");
                UndirListPick.this.efWrklstId.clear();
                UndirListPick.this.efListId.clear();
                UndirListPick.this.efListIdList.clear();
                UndirListPick.this.efWrkref.clear();
                UndirListPick.this.efToId.clear();
                newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                newFrm.run();
            }
            if (UndirListPick.this.efWrklstId.getText().length() > 0) {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efWrklstId.getText(), UndirListPick.this.whId));
                    rs.next();
                    wrkRef = rs.getString("wrkref");
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (wrkRef != null) {
                    UndirListPick.this.efWrkref.setText(wrkRef);
                }
            }
            UndirListPick.this.display.setVariable("GET_SERVICES.invtid", UndirListPick.this.efWrkref.getText());
            UndirListPick.this.display.setVariable("GET_SERVICES.list_id", UndirListPick.this.efWrklstId.getText());
            UndirListPick.this.display.setVariable("GET_SERVICES.exitpnt_typ", "SERVICE-OUTBOUND");
            UndirListPick.this.display.setVariable("GET_SERVICES.exitpnt", "LIST-INITIATION");
            newFrm = UndirListPick.this.display.createFormLogic("GET_SERVICES", MtfConstants.EFlow.SHOW_FORM);
            newFrm.run();
            UndirListPick.this.display.setVariable("GET_SERVICES.list_id", "");
            if (!UndirListPick.this.wmdMtf.existToIdMap(UndirListPick.this.efListId.getText()) && UndirListPick.this.efToId.getText().length() > 0) {
                UndirListPick.this.wmdMtf.setToIdMap(UndirListPick.this.efListId.getText(), UndirListPick.this.efToId.getText());
            }
            try {
                rs = null;
                retStatus = 0;
                rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efWrklstId.getText(), UndirListPick.this.whId));
                rs.next();
                startPalletControl = rs.getString("unique_pallet_id");
            }
            catch (MocaException e) {
                retStatus = e.getErrorCode();
            }
            if (startPalletControl != null) {
                UndirListPick.this.processFormExecution();
            }
            else if (retStatus != 0) {
                UndirListPick.this.display.beep();
                UndirListPick.this.frmMain.displayErrorMessage();
                CMtfUtil.reenter(UndirListPick.this.frmMain.getIdentifier());
            }
            if (CMtfUtil.verify(UndirListPick.this.display.getVariable("global.directed_mode"), "Y") == 0) {
                if (CMtfUtil.length(UndirListPick.this.efListId.getText()) == 0) {
                    UndirListPick.this.display.beep();
                    UndirListPick.this.frmMain.promptMessageAnyKey("errNoListForWorkListID");
                    UndirListPick.this.efPckmod.setText("0");
                    UndirListPick.this.efWrklstId.clear();
                    UndirListPick.this.efListId.clear();
                    UndirListPick.this.efListIdList.clear();
                    UndirListPick.this.efWrkref.clear();
                    UndirListPick.this.efToId.clear();
                    newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                    newFrm.run();
                }
                else if (CMtfUtil.length(UndirListPick.this.efListId.getText()) == 1) {
                    UndirListPick.this.efListStsCheckedFlg.setText("0");
                    if (UndirListPick.this.efListSts.getText().equals("R")) {
                        UndirListPick.this.efListStsCheckedFlg.setText("1");
                    }
                    if (UndirListPick.this.efListSts.getText().equals("I")) {
                        UndirListPick.this.efListStsCheckedFlg.setText("1");
                    }
                    if (CMtfUtil.verify(UndirListPick.this.efListStsCheckedFlg.getText(), "1") == 0) {
                        UndirListPick.this.display.beep();
                        UndirListPick.this.frmMain.promptMessageAnyKey("err11029");
                        UndirListPick.this.efPckmod.setText("0");
                        UndirListPick.this.efWrklstId.clear();
                        UndirListPick.this.efListId.clear();
                        UndirListPick.this.efListIdList.clear();
                        UndirListPick.this.efWrkref.clear();
                        UndirListPick.this.efToId.clear();
                        newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                        newFrm.run();
                    }
                }
            }
            else {
                UndirListPick.this.efPckmod.setText("1");
            }
            if (CMtfUtil.length(UndirListPick.this.efListId.getText()) == 0) {
                UndirListPick.this.efPckmod.setText("0");
                UndirListPick.this.efWrklstId.clear();
                UndirListPick.this.efListId.clear();
                UndirListPick.this.efListIdList.clear();
                UndirListPick.this.efWrkref.clear();
                UndirListPick.this.efToId.clear();
                newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                newFrm.run();
            }
            else {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeCommand(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText()));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (rs != null && rs.next() && rs.getInt("spcfc_cs_flg") == 1) {
                    MocaResults caseToRepleishmentRs = null;
                    retStatus = 0;
                    try {
                        caseToRepleishmentRs = UndirListPick.this.session.executeCommand(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;I)Ljava/lang/String;, UndirListPick.this.display.getVariable("global.wh_id"), UndirListPick.this.efListId.getText(), rs.getInt("mix_cs_flg")));
                    }
                    catch (MocaException e2) {
                        retStatus = e2.getErrorCode();
                    }
                    if (retStatus == 0) {
                        UndirListPick.this.session.setGlobalObjMap("CASEASSIGNMENT", (Object)caseToRepleishmentRs);
                    }
                }
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText(), UndirListPick.this.efListIdList.getText(), UndirListPick.this.efListGrpId.getText(), UndirListPick.this.display.getVariable("global.devcod"), UndirListPick.this.display.getVariable("global.wh_id")));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
            }
            if (retStatus != 0) {
                UndirListPick.this.display.beep();
                UndirListPick.this.frmMain.displayErrorMessage();
                UndirListPick.this.efPckmod.setText("0");
                UndirListPick.this.efWrklstId.clear();
                UndirListPick.this.efListId.clear();
                UndirListPick.this.efListIdList.clear();
                UndirListPick.this.efWrkref.clear();
                UndirListPick.this.efToId.clear();
                newFrm = UndirListPick.this.display.createFormLogic(UndirListPick.this.efBackForm.getText());
                newFrm.run();
            }
            else if (retStatus == 0) {
                try {
                    rs = null;
                    retStatus = 0;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText(), UndirListPick.this.display.getVariable("global.wh_id")));
                    rs.next();
                    UndirListPick.this.efReqnum.setText(rs.getString("reqnum"));
                }
                catch (MocaException e) {
                    retStatus = e.getErrorCode();
                }
                if (rs != null && retStatus == 0) {
                    rs = UndirListPick.this.session.getGlobalMocaResults();
                }
                if (retStatus == 0) {
                    try {
                        rs = null;
                        retStatus = 0;
                        rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efReqnum.getText(), UndirListPick.this.display.getVariable("global.devcod"), UndirListPick.this.display.getVariable("global.wh_id")));
                    }
                    catch (MocaException e) {
                        retStatus = e.getErrorCode();
                    }
                }
                UndirListPick.this.efWrklstId.setEnabled(true);
                UndirListPick.this.efWrklstId.clear();
                UndirListPick.this.efToId.clear();
                UndirListPick.this.efToId.setEnabled(true);
                UndirListPick.this.efPosId.clear();

                /*Changes for SkipPosId */
                log.trace("efWrktyp value is = "+ UndirListPick.this.efWrktyp.getText());
            
                if(UndirListPick.this.efWrktyp.getText().equals("K") && UndirListPick.this.usrSkipUndirClusterPickPolicy() == 1 ){
                UndirListPick.this.processUsrAutomaticBatchBuild();
                } else {
                UndirListPick.this.buildBatch();	
                }
                /* Original 
                if (!UndirListPick.this.starterPalletFlag) {
                    UndirListPick.this.buildBatch();
                }*/
                int is_seq_ord = 0;
                try {
                    rs = null;
                    rs = UndirListPick.this.session.executeDSQL(invokedynamic(makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;, UndirListPick.this.efListId.getText()));
                    rs.next();
                    is_seq_ord = rs.getInt("is_seq_ord");
                }
                catch (MocaException ex) {}
                if (is_seq_ord == 1) {
                    UndirListPick.this.cmdFkeyDone.execute((IContainer)null);
                }
                else {
                    UndirListPick.this.efListId.clear();
                    UndirListPick.this.display.setVariable("INIT_POLICIES.prvfrm", "UNDIR_LIST_PICK");
                    newFrm = UndirListPick.this.display.createFormLogic("UNDIR_LIST_PICK");
                    newFrm.run();
                }
            }
            return false;
        }
    }
