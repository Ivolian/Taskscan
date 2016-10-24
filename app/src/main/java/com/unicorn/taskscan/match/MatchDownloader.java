package com.unicorn.taskscan.match;

import android.app.Activity;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unicorn.taskscan.match.model.Match;
import com.unicorn.taskscan.team.TeamDownloader;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.DialogUtils;
import com.unicorn.taskscan.utils.ResponseHelper;
import com.unicorn.taskscan.utils.ToastUtils;
import com.unicorn.taskscan.volley.SimpleVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MatchDownloader {


    // =================== constructor ===================

    private Activity mActivity;

    public MatchDownloader(Activity mActivity) {
        this.mActivity = mActivity;
    }


    // =================== downloadMatch ===================

    public void downloadMatch() {
        String url = ConfigUtils.getBaseUrl() + "/api/getscanmatch";
        final MaterialDialog mask = DialogUtils.showMask(mActivity, "下载比赛信息中");
        Request request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseStr) {
                        try {
                            mask.dismiss();
                            if (ResponseHelper.isRight(responseStr)) {
                                copeResponse(responseStr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                SimpleVolley.getDefaultErrorListener(mask)
        );
        SimpleVolley.addRequest(request);
    }

    private void copeResponse(String responseStr) throws Exception {
        JSONObject response = new JSONObject(responseStr);
        JSONArray data = response.getJSONArray(Constant.K_DATA);
        List<Match> matches = new Gson().fromJson(data.toString(), new TypeToken<List<Match>>() {
        }.getType());
        if (matches.size() != 0) {
            showMatchSelectDialog(matches);
        } else {
            ToastUtils.show("未查询到比赛信息");
        }
    }


    // =================== showMatchSelectDialog ===================

    private void showMatchSelectDialog(final List<Match> matches) {
        new MaterialDialog.Builder(mActivity)
                .title("选择比赛")
                .items(getMatchNames(matches))
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which != -1) {
                            onMatchSelect(matches.get(which));
                        }
                        return true;
                    }
                })
                .positiveText("选择")
                .show();
    }

    private void onMatchSelect(final Match match) {
        TeamDownloader teamDownloader = new TeamDownloader(mActivity,match);
        teamDownloader.downloadTeam();
    }

    private List<String> getMatchNames(List<Match> matches) {
        List<String> matchNames = new ArrayList<>();
        if (matches != null) {
            for (Match match : matches) {
                matchNames.add(match.getMatch_name());
            }
        }
        return matchNames;
    }

}
