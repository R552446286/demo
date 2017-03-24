package com.bwie.renjue.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.renjue.R;
import com.bwie.renjue.bean.ApkData;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * @author 任珏
 * @date 2017/3/249:47
 */
public class ApkAdapter extends BaseAdapter {
    private Context context;
    private List<ApkData.App> app;
    private ProgressDialog progressDialog;

    public ApkAdapter(Context context, List<ApkData.App> app) {
        this.context = context;
        this.app = app;
    }

    @Override
    public int getCount() {
        return app.size();
    }

    @Override
    public Object getItem(int position) {
        return app.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder v;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.apk_lv_item, null);
            v = new ViewHolder();
            v.download_bt = (Button) convertView.findViewById(R.id.download_bt);
            v.download_name = (TextView) convertView.findViewById(R.id.download_name);
            v.download_shortDes = (TextView) convertView.findViewById(R.id.download_shortDes);
            v.download_size = (TextView) convertView.findViewById(R.id.download_size);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.download_name.setText(app.get(position).name);
        v.download_shortDes.setText(app.get(position).shortDes);
        v.download_size.setText(app.get(position).size);
        v.download_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(app.get(position).url);
                String s = Environment.getExternalStorageDirectory() + "/bwie/";
                File file = new File(s);
                if (!file.exists()) {
                    file.mkdirs();
                }
                params.setSaveFilePath(s);
                params.setAutoRename(true);
                x.http().post(params, new Callback.ProgressCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                        context.startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(context, "更新失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }

                    @Override
                    public void onWaiting() {

                    }

                    @Override
                    public void onStarted() {
                        Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置为水平进行条
                        progressDialog.setMessage("正在下载...");
                        progressDialog.setProgress(0);
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        progressDialog.setMax((int) total);
                        progressDialog.setProgress((int) current);
                    }
                });
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView download_name, download_shortDes, download_size;
        Button download_bt;
    }
}
