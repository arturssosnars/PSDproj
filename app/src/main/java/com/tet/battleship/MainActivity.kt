package com.tet.battleship

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class Cell(var selected: Boolean, var blewUp: Boolean = false)

class MainActivity : AppCompatActivity() {

    private val items: MutableList<Cell> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (a in 0..24) {
            items.add(Cell(false))
        }

        val grid = findViewById<GridView>(R.id.gridView)
        val adapter = CustomAdapter(this, items)
        grid.adapter = adapter
        grid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            if (items[position].blewUp) {
                return@OnItemClickListener
            }
            items[position].selected = !items[position].selected
            items[position].blewUp = true
            adapter.notifyDataSetChanged()
            //TODO cell click action

        }

        val start = findViewById<Button>(R.id.start)
        start.setOnClickListener {
            items.forEach {
                it.selected = false
                it.blewUp = false
            }
            adapter.notifyDataSetChanged()
            //TODO start button action
        }
    }
}

class CustomAdapter(private val context: Context, private val items: List<Cell>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(i: Int): Any {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.grid_item, null)
        val state = view.findViewById<TextView>(R.id.state)
        state.text = if (items[i].selected) "X" else "O"
        return view
    }
}