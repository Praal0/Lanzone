package dorian.guerrero.lanzone.mareu_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import dorian.guerrero.lanzone.R;

public class MeetingAdpater extends RecyclerView.Adapter<MeetingAdpater.MyViewHolder> {
    String data1[],data2[];
    Context mContext;

    public MeetingAdpater(Context ct, String s1[], String s2[]){
        mContext = ct;
        data1 = s1;
        data2 = s2;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.meeting_detail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView myText1, myText2;
        ImageView myImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            myText1 = itemView.findViewById(R.id.description_item);
            myText2 = itemView.findViewById(R.id.participants_item);
            myImage = itemView.findViewById(R.id.circle_item);

        }
    }
}
