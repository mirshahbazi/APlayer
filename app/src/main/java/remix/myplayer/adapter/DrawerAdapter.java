package remix.myplayer.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import remix.myplayer.R;
import remix.myplayer.adapter.holder.BaseViewHolder;
import remix.myplayer.interfaces.OnModeChangeListener;
import remix.myplayer.model.Drawer;
import remix.myplayer.theme.Theme;
import remix.myplayer.theme.ThemeStore;
import remix.myplayer.util.ColorUtil;

/**
 * @ClassName
 * @Description
 * @Author Xiaoborui
 * @Date 2016/10/26 11:05
 */

public class DrawerAdapter extends BaseAdapter<Drawer,DrawerAdapter.DrawerHolder>{
    //当前选中项
    private int mSelectIndex = 0;
    private int[] mImgs = new int[]{R.drawable.drawer_icon_musicbox,R.drawable.drawer_icon_recently,R.drawable.darwer_icon_folder,
                                    R.drawable.darwer_icon_night,R.drawable.darwer_icon_set};
    private int[] mTitles = new int[]{R.string.drawer_song,R.string.drawer_recently,
                                    R.string.drawer_folder,R.string.drawer_night,R.string.drawer_setting};
    public DrawerAdapter(Context Context,int layoutId) {
        super(Context,layoutId);
    }
    private OnModeChangeListener mModeChangeListener;

    public void setOnModeChangeListener(OnModeChangeListener l){
        mModeChangeListener = l;
    }

    public void setSelectIndex(int index){
        mSelectIndex = index;
        notifyDataSetChanged();
    }

    @Override
    protected Drawer getItem(int position) {
        return new Drawer(mTitles[position],mImgs[position]);
    }

    @Override
    protected void convert(final DrawerHolder holder, Drawer drawer, int position) {
        Theme.TintDrawable(holder.mImg, drawer.getImageResID(),ThemeStore.getAccentColor());
        holder.mText.setText(drawer.getTitleResId());
        holder.mText.setTextColor(ThemeStore.isDay() ? ColorUtil.getColor(R.color.gray_34353a) : ThemeStore.getTextColorPrimary());
        holder.mText.setTextColor(ColorUtil.getColor(ThemeStore.isDay() ? R.color.gray_34353a : R.color.white_e5e5e5));
        if(position == 3){
            holder.mSwitch.setVisibility(View.VISIBLE);
            holder.mSwitch.setChecked(!ThemeStore.isDay());
            holder.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(mModeChangeListener != null)
                        mModeChangeListener.OnModeChange(isChecked);
                }
            });
        }
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickLitener.onItemClick(v,holder.getAdapterPosition());
            }
        });
        holder.mRoot.setSelected(mSelectIndex == position);
        holder.mRoot.setBackground(Theme.getPressAndSelectedStateListRippleDrawable(mContext,
                Theme.getShape(GradientDrawable.RECTANGLE, ThemeStore.getDrawerEffectColor()),
                Theme.getShape(GradientDrawable.RECTANGLE, ThemeStore.getDrawerDefaultColor()),
                ThemeStore.getDrawerEffectColor()));
    }

    @Override
    public int getItemCount() {
        return mTitles != null ? mTitles.length : 0;
    }

    static class DrawerHolder extends BaseViewHolder{
        @BindView(R.id.item_img)
        ImageView mImg;
        @BindView(R.id.item_text)
        TextView mText;
        @BindView(R.id.item_switch)
        SwitchCompat mSwitch;
        @BindView(R.id.item_root)
        RelativeLayout mRoot;
        DrawerHolder(View itemView) {
            super(itemView);
        }
    }
}
