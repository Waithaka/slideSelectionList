package com.hkm.slideselection.V1;

import android.app.FragmentManager;
import android.os.Bundle;

import com.hkm.slideselection.worker.SelectChoice;
import com.hkm.slideselection.worker.Util;

/**
 * Created by hesk on 10/9/15.
 */
public class StringControlAdapter extends DynamicAdapter<SelectChoice> {

    public StringControlAdapter(FragmentManager fragmentManager, SelectChoice configuration) {
        super(fragmentManager, configuration);
    }

    public StringControlAdapter(FragmentManager fragmentManager, Bundle mfrombundle) {
        super(fragmentManager);
        firstPage = SimpleSingleList.newInstance(mfrombundle);
        firstPageListConfiguration = Util.fromBundle(mfrombundle);
    }

    public StringControlAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }



    @Override
    public SelectChoice extractBundle() {
        if (firstPage != null && firstPage.getArguments() != null) {
            int[] selection = firstPage.getArguments().getIntArray(SimpleSingleList.SELECTION);
            String[] data = firstPage.getArguments().getStringArray(SimpleSingleList.DATASTRING);
            SelectChoice lv0;
            if (selection.length > 1) {
                lv0 = new SelectChoice(selection);
            } else {
                lv0 = new SelectChoice(selection[0]);
            }
            lv0.setResourceData(data);
            lv0.setLevel(0);
            return lv0;
        }
        return null;
    }

    /**
     * tells if the first page is just a normal page or not
     *
     * @return the type from the level
     */
   /* @Override
    protected StringLv FirstConfiguration() {
        return firstPageListConfiguration;
    }*/
    @Override
    protected SimpleSingleList createList(SelectChoice con) {
        if (con.isMulti()) {
            return SimpleSingleList.newInstance(con.getSelections(), con.getSimpleSource(), getCurrentLevel());
        } else {
            return SimpleSingleList.newInstance(con.getSelection(), con.getSimpleSource(), getCurrentLevel());
        }
    }


}
