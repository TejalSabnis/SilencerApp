package com.mobilecomputing.project.silencerapp.model;

import com.google.android.gms.location.places.PlaceLikelihood;

/**
 * Created by Tejal on 2015-11-17.
 */
public class MyPlaceLikelihood implements Comparable<MyPlaceLikelihood> {

    private PlaceLikelihood placeLikelihood;

    public PlaceLikelihood getPlaceLikelihood() {
        return placeLikelihood;
    }

    public void setPlaceLikelihood(PlaceLikelihood placeLikelihood) {
        this.placeLikelihood = placeLikelihood;
    }

    @Override
    public int compareTo(MyPlaceLikelihood another) {
        Float likelihood = this.getPlaceLikelihood().getLikelihood();
        return likelihood.compareTo(another.getPlaceLikelihood().getLikelihood());
    }
}
