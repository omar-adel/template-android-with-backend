package com.basgeekball.awesomevalidation.helper;

import com.basgeekball.awesomevalidation.model.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Net22 on 10/3/2017.
 */
public class SortList {

    public static  void sortRules(ArrayList<Rule> arrayList) {

          Collections.sort(arrayList,new Comparator<Rule>()

    {
        @Override
        public int compare (Rule o1, Rule o2){
            if(o1.getSequence()>(
                    o2.getSequence()))
            {
                return 1 ;
            }
            else
            if(o1.getSequence()<(
                    o2.getSequence()))
            {
                return -1 ;
            }
            return 0;
    }
    });

 }

}

