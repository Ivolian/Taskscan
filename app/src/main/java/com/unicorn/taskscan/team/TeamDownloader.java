package com.unicorn.taskscan.team;

import android.app.Activity;
import android.net.Uri;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

    public TeamDownloader(Activity mActivity) {
        this.mActivity = mActivity;
    }


    // =================== downloadTeam ===================

    public void downloadTeam(final String matchId) {
        String url = getDownloadTeamUrl(matchId);
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
        RecordHelper.saveTeam(teams);
        showTeamDownloadResult(teams);
    }

    private void showTeamDownloadResult(final List<Team> teams) {
        new MaterialDialog.Builder(mActivity)
                .title("下载完成")
                .content(getContent(teams))
                .positiveText("确认")
                .show();
    }

    private String getContent(final List<Team> teams) {
        return "比赛名称: "
                + teams.get(0).getMatch_name()
                + "\r\n"
                + "下载队伍数量: "
                + teams.size();
    }


}
