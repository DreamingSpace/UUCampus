package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.SuggestionItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class SuggestionRes {
private List<SuggestionItem>suggestion;

    public List<SuggestionItem> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(List<SuggestionItem> suggestion) {
        this.suggestion = suggestion;
    }
}
