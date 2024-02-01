package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HourAdapter extends ArrayAdapter<hourEvent> {
    public HourAdapter(@NonNull Context context, List<hourEvent> HourEvents) {
        super(context, 0, HourEvents);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        hourEvent event = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hour_cell, parent, false);

        setHour(convertView, event.time);
        setEvents(convertView, event.events);

        return convertView;
    }
    private void setHour(View convertView, LocalTime time) {
        TextView timeTV = convertView.findViewById(R.id.timeTV);
        timeTV.setText(CalendarUtils.timeFormattor(time));
    }

    private void setEvents(View convertView, ArrayList<Events> events) {
        TextView event1 = convertView.findViewById(R.id.event1);
        TextView event2 = convertView.findViewById(R.id.event2);
        TextView event3 = convertView.findViewById(R.id.event3);

        if (events.size() == 0) {
            hideEvent(event1);
            hideEvent(event2);
            hideEvent(event3);
        }
        else if (events.size() == 1){
            setEvent(event1, events.get(0));
            hideEvent(event2);
            hideEvent(event3);
        }
        else if (events.size() == 2) {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            hideEvent(event3);
        }
        else if (events.size() == 3) {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            setEvent(event3, events.get(2));
        }
        else {
            setEvent(event1, events.get(0));
            setEvent(event2, events.get(1));
            event3.setVisibility(event3.VISIBLE);
            String eventNotShown = String.valueOf(events.size() - 2);
            eventNotShown += "More Events";
            event3.setText(eventNotShown);
        }
    }

    private void setEvent(TextView textView, Events event) {
        textView.setText(event.getEventNameET());
        textView.setVisibility(TextView.VISIBLE);
    }
    private void hideEvent(TextView tv) {
        tv.setVisibility(TextView.VISIBLE);
    }
}