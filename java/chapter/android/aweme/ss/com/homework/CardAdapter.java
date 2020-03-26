package chapter.android.aweme.ss.com.homework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.NumberViewHolder> {

    private static final String TAG = "CardAdapter";
    private int mNumberItems;
    private final ListItemClickListener mOnClickListener;
    List<Message> data;
    private static int viewHolderCount;
    
    public CardAdapter(int numListItems, ListItemClickListener listener, List<Message> messages) {
        mNumberItems = messages.size();
        mOnClickListener = listener;
        viewHolderCount = 0;

        data = messages;
    }



    /*
     * 一般会预留2~4个ViewHolder，off screen的数量由mCachedSize来决定
     *
     * The number of ViewHolders that have been created. Typically, you can figure out how many
     * there should be by determining how many list items fit on your screen at once and add 2 to 4
     * to that number. That isn't the exact formula, but will give you an idea of how many
     * ViewHolders have been created to display any given RecyclerView.
     *
     * Here's some ASCII art to hopefully help you understand:
     *
     *    ViewHolders on screen:
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 0 |
     *        *-----------------------------*
     *        |         ViewHolder index: 1 |
     *        *-----------------------------*
     *        |         ViewHolder index: 2 |
     *        *-----------------------------*
     *        |         ViewHolder index: 3 |
     *        *-----------------------------*
     *        |         ViewHolder index: 4 |
     *        *-----------------------------*
     *        |         ViewHolder index: 5 |
     *        *-----------------------------*
     *        |         ViewHolder index: 6 |
     *        *-----------------------------*
     *        |         ViewHolder index: 7 |
     *        *-----------------------------*
     *
     *    Extra ViewHolders (off screen)
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 8 |
     *        *-----------------------------*
     *        |         ViewHolder index: 9 |
     *        *-----------------------------*
     *        |         ViewHolder index: 10|
     *        *-----------------------------*
     *        |         ViewHolder index: 11|
     *        *-----------------------------*
     *
     *    index:12 from where?
     *
     *    Total number of ViewHolders = 12
     *
     *
     *    不做特殊处理：最多缓存多少个ViewHolder N(第一屏可见) + 2 mCachedSize + 5*itemType RecyclePool
     *
     *    找到position一致的viewholder才可以复用，新的位置由于position不一致，所以不能复用，重新创建新的
     *    这也是为什么 mCachedViews一开始缓存的是0、1    所以 8、9、10需要被创建，
     *    那为什么10 和 11也要被创建？
     *
     *    当view完全不可见的时候才会被缓存回收，这与item触发getViewForPosition不同，
     *    当2完全被缓存的时候，实际上getViewForPosition已经触发到11了，此时RecyclePool有一个viewholder(可以直接被复用)
     *    当12触发getViewForPosition的时候，由于RecyclePool里面有，所以直接复用这里的viewholder
     *    问题？复用的viewholder到底是 0 1 2当中的哪一个？
     *
     *
     *    RecycleView 对比 ListView 最大的优势,缓存的设计,减少bindView的处理
     */

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.im_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        String name = data.get(viewHolderCount).getTitle();
        viewHolder.nameText.setText(name);
        String content = data.get(viewHolderCount).getDescription();
        viewHolder.contentText.setText(content);
        String time = data.get(viewHolderCount).getTime();
        viewHolder.timeText.setText(time);
        String logo = data.get(viewHolderCount).getIcon();
        Log.d("icon", logo);
        if(logo.equals("TYPE_ROBOT")){

            viewHolder.userImage.setImageResource(R.drawable.session_robot);
        }
        if(logo.equals("TYPE_STRANGER")){

            viewHolder.userImage.setImageResource(R.drawable.session_stranger);
        }
        if(logo.equals("TYPE_SYSTEM")){

            viewHolder.userImage.setImageResource(R.drawable.session_system_notice);
        }

        boolean isOffice = data.get(viewHolderCount).isOfficial();
        if(isOffice) {
            viewHolder.officeImage.setVisibility(view.VISIBLE);
        }
        //viewHolder..setText("ViewHolder index: " + viewHolderCount);

        //int backgroundColorForViewHolder = ColorUtils.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        int backgroundColorForViewHolder = 7;
        //viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: " + viewHolderCount);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        Log.d(TAG, "onBindViewHolder: #" + position);
        numberViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CircleImageView userImage;      //头像
        private final ImageView officeImage;
        private final TextView nameText; //姓名
        private final TextView contentText; //消息内容
        private final TextView timeText;          //时间
        //private final TextView headText;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            //用户头像
            userImage = (CircleImageView)itemView.findViewById(R.id.iv_avatar);
            nameText = (TextView) itemView.findViewById(R.id.tv_title);
            contentText = (TextView) itemView.findViewById(R.id.tv_description);
            timeText = (TextView) itemView.findViewById(R.id.tv_time);
            officeImage = (ImageView)itemView.findViewById(R.id.robot_notice);
            //headText = (TextView) itemView.findViewById(R.id.head);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            //contentText.setText(String.valueOf(position));


//            viewHolderIndex.setText(String.format("ViewHolder index: %s", getAdapterPosition()));
//            int backgroundColorForViewHolder = ColorUtils.
//                    getViewHolderBackgroundColorFromInstance(itemView.getContext(), getAdapterPosition() % 10);
//            itemView.setBackgroundColor(backgroundColorForViewHolder);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
