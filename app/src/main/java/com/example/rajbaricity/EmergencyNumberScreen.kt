package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data model for emergency contact
data class EmergencyContact(
    val title: String,
    val details: List<String>,
    val icon: String = "",
    val color: Color = Color.Black
)

@Composable
fun EmergencyNumberScreen(onHomeClick: () -> Unit = {}) {
    val tabTitles = listOf("ফায়ার সার্ভিস", "পুলিশ স্টেশন", "বিদ্যুৎ অফিস", "অন্যান্য সার্ভিস")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "🚨 জরুরি নাম্বার ও হেল্পলাইন",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        TabRow(selectedTabIndex = selectedTabIndex, containerColor = Color(0xFFECEFF1)) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(text = title, fontSize = 16.sp) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> EmergencyListView(getFireServiceData())
            1 -> EmergencyListView(getPoliceData())
            2 -> EmergencyListView(getElectricityData())
            3 -> EmergencyListView(getOthersData())
        }
    }
}

@Composable
fun EmergencyListView(contacts: List<EmergencyContact>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        contacts.forEach { contact ->
            EmergencyItem(contact)
        }
    }
}

@Composable
fun EmergencyItem(contact: EmergencyContact) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text("${contact.icon} ${contact.title}", fontSize = 18.sp, color = contact.color)
        contact.details.forEach { line ->
            Text(line, fontSize = 14.sp)
        }
    }
}

// 🔥 Fire Service Data
fun getFireServiceData(): List<EmergencyContact> = listOf(
    EmergencyContact("রাজবাড়ী সদর", listOf("ফায়ার সার্ভিস: 01901021024"), "🔥", Color.Red),
    EmergencyContact("পাংশা", listOf("ফায়ার সার্ভিস: 01901021030"), "🔥", Color.Red),
    EmergencyContact("কালুখালি", listOf("ফায়ার সার্ভিস: 01785575717"), "🔥", Color.Red),
    EmergencyContact("বালিয়াকান্দি", listOf("ফায়ার সার্ভিস: 01778400902"), "🔥", Color.Red),
    EmergencyContact("গোয়ালন্দ", listOf("ফায়ার সার্ভিস: 01712223100"), "🔥", Color.Red)
)

// 👮 Police Data
fun getPoliceData(): List<EmergencyContact> = listOf(
    EmergencyContact("সদর থানা", listOf("ডিউটি অফিসার: 01320101374", "অফিসার ইনচার্জ: 01320101369", "ট্রাফিক ইন্সপেক্টর: 01320101529"), "👮", Color(0xFF1A237E)),
    EmergencyContact("পাংশা থানা", listOf("ডিউটি অফিসার: 01320101426", "অফিসার ইনচার্জ: 013201011421", "ট্রাফিক ইন্সপেক্টর: ০১৭৪৫৫xxxx"), "👮", Color(0xFF1A237E)),
    EmergencyContact("কালুখালি থানা", listOf("ডিউটি অফিসার: 01320101452", "অফিসার ইনচার্জ: 01320101447", "ট্রাফিক ইন্সপেক্টর: ০১৭৪৫৫xxxx"), "👮", Color(0xFF1A237E)),
    EmergencyContact("বালিয়াকান্দি থানা", listOf("ডিউটি অফিসার: 01320101400", "অফিসার ইনচার্জ: 01320101369", "ট্রাফিক ইন্সপেক্টর: ০১৭৪৫৫xxxx"), "👮", Color(0xFF1A237E)),
    EmergencyContact("গোয়ালন্দ থানা", listOf("ডিউটি অফিসার: 01320101452", "অফিসার ইনচার্জ: 01320101447", "ট্রাফিক ইন্সপেক্টর: ০১৭৪৫৫xxxx"), "👮", Color(0xFF1A237E))
)

// ⚡ Electricity Data
fun getElectricityData(): List<EmergencyContact> = listOf(
    EmergencyContact(
        title = "রাজবাড়ী সদর বিদ্যুৎ অফিস",
        details = listOf(
            "Complain Number: 01315655630",
            "সাব-স্টেশন অফিসার: 01700-xxxxxx",
            "লাইনম্যান (ডিউটি): 01720-xxxxxx"
        ),
        icon = "⚡",
        color = Color(0xFF33691E)
    )
)
//    EmergencyContact(
//        title = "পাংশা বিদ্যুৎ অফিস",
//        details = listOf(
//            "সাব-স্টেশন অফিসার: ০১৭xx-xxxxxx",
//            "লাইনম্যান (ডিউটি): ০১৮xx-xxxxxx"
//        ),
//        icon = "⚡",
//        color = Color(0xFF33691E)
//    ),
//    EmergencyContact(
//        title = "কালুখালী বিদ্যুৎ অফিস",
//        details = listOf(
//            "সাব-স্টেশন অফিসার: ০১৭xx-xxxxxx",
//            "লাইনম্যান (ডিউটি): ০১৮xx-xxxxxx"
//        ),
//        icon = "⚡",
//        color = Color(0xFF33691E)
//    ),
//    EmergencyContact(
//        title = "বালিয়াকান্দি বিদ্যুৎ অফিস",
//        details = listOf(
//            "সাব-স্টেশন অফিসার: ০১৭xx-xxxxxx",
//            "লাইনম্যান (ডিউটি): ০১৮xx-xxxxxx"
//        ),
//        icon = "⚡",
//        color = Color(0xFF33691E)
//    ),
//    EmergencyContact(
//        title = "গোয়ালন্দ বিদ্যুৎ অফিস",
//        details = listOf(
//            "সাব-স্টেশন অফিসার: ০১৭xx-xxxxxx",
//            "লাইনম্যান (ডিউটি): ০১৮xx-xxxxxx"
//        ),
//        icon = "⚡",
//        color = Color(0xFF33691E)
//    )
//)

// 📞 Others
fun getOthersData(): List<EmergencyContact> = listOf(
    EmergencyContact(
        title = "অন্যান্য জরুরি সার্ভিস",
        details = listOf(
            "1. সেবা (সরকারি হেল্পলাইন) - 16263",
            "2. জাতীয় জরুরি সেবা - 999",
            "3. অ্যাম্বুলেন্স সার্ভিস (সরকারি সদর হাসপাতাল) - 01712610585",
            "4. জাতীয় তথ্য সেবা - 333",
            "5. দুর্নীতি দমন কমিশন (দুদক) - 106",
            "6. জাতীয় ভোক্তা অধিকার সংরক্ষণ অধিদপ্তর, রাজবাড়ী - 01318396940",
            "7. ভোক্তা অধিকার হটলাইন - 16121",
            "8. পরিছন্নতা কর্মী (সুইপার) - 017xxxxxxxx",
            "9. সদর হাসপাতাল (ইমার্জেন্সি) - 01730324789",
            "10. মাদকদ্রব্য নিয়ন্ত্রণ অধিদপ্তর - 01908888888",
            "11. নিরাপদ খাদ্য কর্তৃপক্ষ - 16155"


        ),
        icon = "📞",
        color = Color(0xFFB71C1C)
    )
)
