package com.example.technicaltest2.data.local
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import com.example.technicaltest2.R
import com.example.technicaltest2.data.model.Student

object StudentData {

    val students = listOf(
        Student("Alice Johnson", "45 Maple Ave, Springfield", R.drawable.female1),
        Student("Bob Smith", "22 Pine Street, Rivertown", R.drawable.male1),
        Student("Catherine Lee", "78 Oak Drive, Hillcrest", R.drawable.female2),
        Student("David Brown", "56 Birch Lane, Greenfield", R.drawable.male2),
        Student("Ella Davis", "12 Elm Street, Lakeside", R.drawable.female3),
        Student("Frank Miller", "90 Cedar Ave, Brookville", R.drawable.male3),
        Student("Grace Wilson", "34 Walnut Road, Meadowland", R.drawable.female4),
        Student("Henry Martinez", "67 Ash Blvd, Sunnydale", R.drawable.male4),
        Student("Isla Thompson", "89 Willow Way, Riverside", R.drawable.female5),
        Student("Jack White", "23 Chestnut Court, Pleasantville", R.drawable.male5)
    )

}