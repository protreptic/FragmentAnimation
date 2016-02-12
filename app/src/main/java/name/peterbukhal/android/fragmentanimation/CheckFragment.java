package name.peterbukhal.android.fragmentanimation;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 11/02/16 13:28 by
 *
 * @author Peter Bukhal (petr@taxik.ru)
 */
public class CheckFragment extends Fragment {

    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_CHECK = "arg_check";

    public static CheckFragment newInstance(String title, Check check) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TITLE, title);
        arguments.putParcelable(ARG_CHECK, check);

        CheckFragment fragment = new CheckFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    private String title;
    private Check check;

    private StaticListView slvCheckPositions;
    private AppCompatTextView tvTotalPrice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check, container, false);

        ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollVew);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

        slvCheckPositions = (StaticListView) rootView.findViewById(R.id.check);
        tvTotalPrice = (AppCompatTextView) rootView.findViewById(R.id.totalPrice);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(ARG_TITLE, title);
        outState.putParcelable(ARG_CHECK, check);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(ARG_TITLE)) {
            title = savedInstanceState.getString(ARG_TITLE);
        } else if (getArguments() != null
                && getArguments().containsKey(ARG_TITLE)) {
            title = getArguments().getString(ARG_TITLE);
        }

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null && title != null) {
            actionBar.setTitle(title);
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_CHECK)) {
            check = savedInstanceState.getParcelable(ARG_CHECK);
        } else if (getArguments() != null && getArguments().containsKey(ARG_CHECK)) {
            check = getArguments().getParcelable(ARG_CHECK);
        }

        if (check != null) {
            slvCheckPositions.setAdapter(new CheckAdapter(getContext(), check.getPositions()));
            tvTotalPrice.setText(check.getTotalPrice());

            if (!TextUtils.isEmpty(check.getBonuses())) {
                ViewGroup bonusesView = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.layout_check_position, slvCheckPositions, false);

                AppCompatTextView text1 = (AppCompatTextView) bonusesView.findViewById(R.id.text1);
                text1.setText("За поездку начислено");
                text1.setTextAppearance(getActivity(), R.style.TextAppearanceBody2);

                AppCompatTextView text2 = (AppCompatTextView) bonusesView.findViewById(R.id.text2);
                text2.setText(check.getBonuses());
                text2.setTextAppearance(getActivity(), R.style.TextAppearanceBody2);
                text2.setTextColor(getResources().getColor(R.color.colorTextAccent));

                slvCheckPositions.addView(bonusesView);
            }
        }
    }

    public static class Check implements Parcelable {

        private final String totalPrice;
        private final String bonuses;
        private final List<CheckPosition> positions = new ArrayList<>();

        public Check(String totalPrice, String bonuses, List<CheckPosition> positions) {
            this.totalPrice = totalPrice;
            this.bonuses = bonuses;
            this.positions.addAll(positions);
        }

        protected Check(Parcel in) {
            totalPrice = in.readString();
            bonuses = in.readString();
            positions.addAll(in.createTypedArrayList(CheckPosition.CREATOR));
        }

        public static final Creator<Check> CREATOR = new Creator<Check>() {
            @Override
            public Check createFromParcel(Parcel in) {
                return new Check(in);
            }

            @Override
            public Check[] newArray(int size) {
                return new Check[size];
            }
        };

        public String getTotalPrice() {
            return totalPrice;
        }

        public List<CheckPosition> getPositions() {
            return positions;
        }

        public String getBonuses() {
            return bonuses;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(totalPrice);
            dest.writeString(bonuses);
            dest.writeTypedList(positions);
        }

    }

    public static class CheckPosition implements Parcelable {

        private final String name;
        private final String price;

        public CheckPosition(String name, String price) {
            this.name = name;
            this.price = price;
        }

        protected CheckPosition(Parcel in) {
            name = in.readString();
            price = in.readString();
        }

        public static final Creator<CheckPosition> CREATOR = new Creator<CheckPosition>() {
            @Override
            public CheckPosition createFromParcel(Parcel in) {
                return new CheckPosition(in);
            }

            @Override
            public CheckPosition[] newArray(int size) {
                return new CheckPosition[size];
            }
        };

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(price);
        }

    }

    private static class CheckAdapter extends BaseAdapter {

        private List<CheckPosition> checkPositions = new ArrayList<>();
        private LayoutInflater inflater;

        public CheckAdapter(Context context, List<CheckPosition> positions) {

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            checkPositions.addAll(positions);
        }

        @Override
        public int getCount() {
            return checkPositions.size();
        }

        @Override
        public CheckPosition getItem(int position) {
            return checkPositions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            CheckItemHolder holder;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.layout_check_position, parent, false);

                holder = new CheckItemHolder();
                holder.text1 = (AppCompatTextView) convertView.findViewById(R.id.text1);
                holder.text2 = (AppCompatTextView) convertView.findViewById(R.id.text2);

                convertView.setTag(holder);
            } else {

                holder = (CheckItemHolder) convertView.getTag();
            }

            CheckPosition checkPosition = getItem(position);

            holder.text1.setText(checkPosition.getName());
            holder.text2.setText(checkPosition.getPrice());

            return convertView;
        }

    }

    private static class CheckItemHolder {

        AppCompatTextView text1;
        AppCompatTextView text2;

    }

}
