package com.waveaccess.test.fragments

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.waveaccess.test.App
import com.waveaccess.test.R
import com.waveaccess.test.data.User
import com.waveaccess.test.viewmodels.UserViewModel
import com.waveaccess.test.viewmodels.ViewModelFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

private const val USER_ID = "user_id"

class UserFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UserViewModel
    private var userId: Int = 0
    private lateinit var nameTv: TextView
    private lateinit var ageTv: TextView
    private lateinit var companyTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var phoneTv: TextView
    private lateinit var addressTv: TextView
    private lateinit var aboutTv: TextView
    private lateinit var eyeColor: View
    private lateinit var favFruitIv: ImageView
    private lateinit var registeredTv: TextView
    private lateinit var locationTv: TextView
    private lateinit var friendsBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        setHasOptionsMenu(true)
        arguments?.let {
            userId = it.getInt(USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTv = view.findViewById(R.id.user_name_tv)
        ageTv = view.findViewById(R.id.user_age_tv)
        companyTv = view.findViewById(R.id.user_company_tv)
        emailTv = view.findViewById(R.id.user_email_tv)
        phoneTv = view.findViewById(R.id.user_phone_tv)
        addressTv = view.findViewById(R.id.user_address_tv)
        aboutTv = view.findViewById(R.id.user_about_tv)
        eyeColor = view.findViewById(R.id.user_eye_color_view)
        favFruitIv = view.findViewById(R.id.user_favourite_fruit_iv)
        registeredTv = view.findViewById(R.id.user_registered_tv)
        locationTv = view.findViewById(R.id.location_tv)
        friendsBtn = view.findViewById(R.id.friends_btn)
        val userDataObserver = Observer<User> {
            nameTv.text = it.name
            ageTv.text = it.age
            companyTv.text = it.company
            setEmail(it.email)
            setPhone(it.phone)
            addressTv.text = it.address
            aboutTv.text = it.about
            eyeColor.setBackgroundColor(eyeColor(it.eye_color))
            favFruitIv.setBackgroundResource(favFruit(it.favorite_fruit))
            registeredTv.text = formatTime(it.registered)
            setLocation(it.latitude, it.longitude)
            setFriendsButton(it.friendsIds?: listOf(), it.name?: "")
        }
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.loadUser(userId)
    }

    private fun setPhone(phone: String?) {
        phoneTv.text = phone
        phoneTv.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $phone"))
            startActivity(intent)
        }
    }

    private fun setEmail(email: String?) {
        emailTv.text = email
        emailTv.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto: $email"))
            startActivity(intent)
        }
    }

    private fun setLocation(lat: Double, lng: Double){
        val ls = LocationConverter.latitudeAsDMS(lat) + " " + LocationConverter.longitudeAsDMS(lng)
        locationTv.text = ls
        locationTv.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:$lat, $lng")
//            val gmmIntentUri = Uri.parse("geo:59.5557861328125, 30.010909080505371")

            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun formatTime(t: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z", Locale.US)
        val newSdf = SimpleDateFormat("HH:mm dd.MM.yy", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        var formatted = ""
        try {
            val date = sdf.parse(t)
            if(date != null) {
                formatted = newSdf.format(date)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return formatted
    }

    private fun favFruit(f: String): Int{
        return when(f){
            "banana" -> R.drawable.banana_64
            "apple" -> R.drawable.apple_64
            "strawberry" -> R.drawable.strawberry_64
            else -> android.R.drawable.ic_menu_help
        }
    }
    private fun eyeColor(col: String): Int{
        return when(col){
            "brown" -> ContextCompat.getColor(requireContext(), R.color.brown)
            "green" -> ContextCompat.getColor(requireContext(), R.color.green)
            "blue" -> ContextCompat.getColor(requireContext(), R.color.blue)
            else -> ContextCompat.getColor(requireContext(), R.color.grey)
        }
    }

    private fun setFriendsButton(list: List<Int>, name: String) {
        val al = arrayListOf<Int>()
        al.addAll(list)
        friendsBtn.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.beginTransaction()
                ?.add(R.id.fragment_container_view, UsersFragment.newInstance(al, name))
                ?.addToBackStack(null)
                ?.commit()
        }
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }
    object LocationConverter {

        fun latitudeAsDMS(latitude: Double): String {
            val direction = if (latitude > 0) "N" else "S"
            var strLatitude = Location.convert(latitude.absoluteValue, Location.FORMAT_SECONDS)
            strLatitude = replaceDelimiters(strLatitude)
            strLatitude += direction
            return strLatitude
        }

        fun longitudeAsDMS(longitude: Double): String {
            val direction = if (longitude > 0) "W" else "E"
            var strLongitude = Location.convert(longitude.absoluteValue, Location.FORMAT_SECONDS)
            strLongitude = replaceDelimiters(strLongitude)
            strLongitude += direction
            return strLongitude
        }

        private fun replaceDelimiters(str: String): String {
            var s = str
            s = s.replaceFirst(":".toRegex(), "°")
            s = s.replaceFirst(":".toRegex(), "'")
            s += "\""
            return s
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: Int) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
    }
}