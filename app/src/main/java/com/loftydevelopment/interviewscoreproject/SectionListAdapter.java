package com.loftydevelopment.interviewscoreproject;

import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class SectionListAdapter extends BaseAdapter {

    public final Map<String, Adapter> sections = new LinkedHashMap<>();
    public ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;

    public SectionListAdapter(Context context) {

        headers = new ArrayAdapter<>(context, R.layout.list_header);

    }

    public void addSection(String section, Adapter adapter) {

        this.headers.add(section);
        this.sections.put(section, adapter);

    }

    public Object getItem(int position) {

        for (Object section : this.sections.keySet()) {

            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return section;
            if (position < size) return adapter.getItem(position - 1);

            // otherwise jump into next section
            position -= size;
        }

        return null;
    }

    public int getCount() {

        // total together all sections, plus one for each section header
        int total = 0;
        for (Adapter adapter : this.sections.values())
            total += adapter.getCount() + 1;

        return total;
    }

    @Override
    public int getViewTypeCount() {

        // assume that headers count as one, then total all sections
        int total = 1;
        for (Adapter adapter : this.sections.values())
            total += adapter.getViewTypeCount();

        return total;
    }

    @Override
    public int getItemViewType(int position) {

        int type = 1;
        for (Object section : this.sections.keySet()) {

            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return TYPE_SECTION_HEADER;
            if (position < size) return type + adapter.getItemViewType(position - 1);

            // otherwise jump into next section
            position -= size;
            type += adapter.getViewTypeCount();

        }

        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int sectionnum = 0;

        for (Object section : this.sections.keySet()) {

            Adapter adapter = sections.get(section);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return headers.getView(sectionnum, convertView, parent);
            if (position < size) return adapter.getView(position - 1, convertView, parent);

            // otherwise jump into next section
            position -= size;
            sectionnum++;

        }

        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}