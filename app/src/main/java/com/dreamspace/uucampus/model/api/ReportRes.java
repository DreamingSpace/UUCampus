package com.dreamspace.uucampus.model.api;

import com.dreamspace.uucampus.model.ReportItem;

import java.util.List;

/**
 * Created by wufan on 2015/9/29.
 */
public class ReportRes {
    private List<ReportItem>report;

    public List<ReportItem> getReport() {
        return report;
    }

    public void setReport(List<ReportItem> report) {
        this.report = report;
    }
}
