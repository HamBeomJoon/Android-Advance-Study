package com.example.myapplication

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log

class MyContentProvider(private val context: Context) {

    // 연락처 정보를 가져오는 메서드
    fun getContacts() {
        // ContentResolver를 사용하여 연락처 정보를 가져옴
        val resolver: ContentResolver = context.contentResolver
        val phoneUri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        // 쿼리를 수행하여 커서를 가져옴
        val cursor: Cursor? = resolver.query(phoneUri, projection, null, null, null)
        cursor?.use {
            while (it.moveToNext()) {
                // 컬럼 인덱스를 통해 데이터를 가져옴
                val nameIndex = it.getColumnIndex(projection[1])
                val numberIndex = it.getColumnIndex(projection[2])
                val name = it.getString(nameIndex)
                var number = it.getString(numberIndex)
                number = number.replace("-", "")
                Log.d("GetContact", "이름 : $name 번호 : $number")
            }
        }
    }
}
