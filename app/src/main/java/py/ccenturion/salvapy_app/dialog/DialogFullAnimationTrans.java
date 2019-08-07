package py.ccenturion.salvapy_app.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import py.ccenturion.salvapy_app.R;
import py.ccenturion.salvapy_app.util.eventbus.Events;
import py.ccenturion.salvapy_app.util.eventbus.GlobalBus;
/**
 * Created by Gustavo on 9/28/17.
 */

public class DialogFullAnimationTrans extends Dialog {

    public DialogFullAnimationTrans(Context context) {
        super(context, R.style.dialogFull);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_full_animation_trans);
    }

    /**
     * Cerramos el dialogo
     */
    private void dismissDialog(){
        DialogFullAnimationTrans.this.dismiss();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onModelChange(Events.showAnimationVale event){
        if(event.status == 1){
            // animamos el fadeout
        }else if(event.status == 2) {
            dismissDialog();
        }
    }
}
