package com.unicorn.taskscan.team;

import android.app.Activity;
import android.net.Uri;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unicorn.taskscan.match.model.Match;
import com.unicorn.taskscan.record.RecordHelper;
import com.unicorn.taskscan.team.model.Team;
import com.unicorn.taskscan.utils.ConfigUtils;
import com.unicorn.taskscan.utils.Constant;
import com.unicorn.taskscan.utils.DialogUtils;
import com.unicorn.taskscan.utils.ResponseHelper;
import com.unicorn.taskscan.volley.SimpleVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TeamDownloader {


    // =================== constructor ===================

    private Activity mActivity;

    private Match match;

    public TeamDownloader(Activity mActivity, Match match) {
        this.mActivity = mActivity;
        this.match = match;
    }


    // =================== downloadTeam ===================

    public void downloadTeam() {
        String url = getDownloadTeamUrl(match.getMatch_id());
        final MaterialDialog mask = DialogUtils.showMask(mActivity, "下载队伍信息中");
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

    private String getDownloadTeamUrl(final String matchId) {
        Uri.Builder builder = Uri.parse(ConfigUtils.getBaseUrl() + "/api/getscanteam?").buildUpon();
        builder.appendQueryParameter(Constant.K_MATCH_ID, matchId);
        return builder.toString();
    }

    private void copeResponse(String responseStr) throws Exception {
        JSONObject response = new JSONObject(responseStr);
        JSONArray data = response.getJSONArray(Constant.K_DATA);
        List<Team> teams = new Gson().fromJson(data.toString(), new TypeToken<List<Team>>() {
        }.getType());
        if (teams.size() != 0) {
            copeTeams(teams);
        }
    }

    private void copeTeams(List<Team> teams) throws Exception {
        RecordHelper.saveTeam(match, teams);
        showDownloadResult(match, teams);
    }

    private void showDownloadResult(final Match match, final List<Team> teams) {
        new MaterialDialog.Builder(mActivity)
                .title("下载完成")
                .content(getContent(match, teams))
                .positiveText("确认")
                .show();
    }

    private String getContent(final Match match, final List<Team> teams) {
        return "比赛名称: "
                + match.getMatch_name()
                + "\r\n"
                + "下载队伍数量: "
                + teams.size();
    }


}
