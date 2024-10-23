package com.example.ticketbooking.Activity

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ticketbooking.Adapter.DateAdapter
import com.example.ticketbooking.Adapter.SeatListAdapter
import com.example.ticketbooking.Adapter.TimeAdapter
import com.example.ticketbooking.Models.Film
import com.example.ticketbooking.Models.Seat
import com.example.ticketbooking.databinding.ActivitySeatListBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SeatListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatListBinding
    private lateinit var film: Film
    private var price: Double = 0.0
    private var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize window settings
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        // Retrieve film data and initialize UI
        getIntentExtra()
        setVariables()
        initSeatsList()
    }

    private fun initSeatsList() {
        // Setting up GridLayoutManager for seat list
        val gridLayoutManager = GridLayoutManager(this, 7)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1 // Ensures each item takes up only 1 column
            }
        }

        // Setup RecyclerView for seats
        binding.seatRecyclerview.layoutManager = gridLayoutManager
        val seatList = generateSeatList()
        val seatAdapter = SeatListAdapter(seatList, this, object : SeatListAdapter.SelectedSeat {
            override fun Return(selectedName: String, num: Int) {
                binding.numberSelectedTxt.text = "$num Seat Selected"
                val df = DecimalFormat("#.##")
                price = df.format(num * film.price).toDouble()
                number = num
                binding.priceTxt.text = "$price"
            }
        })

        // Assign adapter to the RecyclerView
        binding.seatRecyclerview.adapter = seatAdapter
        binding.seatRecyclerview.isNestedScrollingEnabled = false

        // Initialize the Time RecyclerView
        binding.timeRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.timeRecyclerview.adapter = TimeAdapter(generateTimeSlots())

        // Initialize the Date RecyclerView
        binding.dateRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.dateRecyclerview.adapter = DateAdapter(generateDates())
    }

    private fun setVariables() {
        // You can set any initial UI-related variables here if needed
    }

    private fun getIntentExtra() {
        // Retrieve Film object from intent extras
        film = intent.getParcelableExtra("film")!!
    }

    private fun generateSeatList(): List<Seat> {
        // Generate seat list with status
        val seatList = mutableListOf<Seat>()
        val numberSeats = 81
        for (i in 0 until numberSeats) {
            val seatStatus = if (i == 2 || i == 20 || i == 33 || i == 41 || i == 50 || i == 72 || i == 73)
                Seat.SeatStatus.UNAVAILABLE
            else
                Seat.SeatStatus.AVAILABLE

            seatList.add(Seat(seatStatus, ""))
        }
        return seatList
    }

    private fun generateTimeSlots(): List<String> {
        // Generate time slots formatted as "hh:mm a"
        val timeSlots = mutableListOf<String>()
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        for (i in 0 until 24 step 2) {
            val time = LocalTime.of(i, 0)
            timeSlots.add(time.format(formatter))
        }
        return timeSlots
    }

    private fun generateDates(): List<String> {
        // Generate a list of dates starting from today
        val dates = mutableListOf<String>()
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("EEE/dd/MMM")
        for (i in 0 until 7) {
            dates.add(today.plusDays(i.toLong()).format(formatter))
        }
        return dates
    }
}
