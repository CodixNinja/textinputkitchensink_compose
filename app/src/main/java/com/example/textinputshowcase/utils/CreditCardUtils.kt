/*
 * Copyright (c) 2025, Seth Ladd
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 */

package com.example.textinputshowcase.utils

object CreditCardUtils {
    fun formatCreditCardNumber(number: String): String {
        val digitsOnly = number.filter { it.isDigit() }
        return digitsOnly.chunked(4).joinToString(" ").take(19) // 16 digits + 3 spaces
    }

    fun formatExpiryDate(input: String): String {
        val digitsOnly = input.filter { it.isDigit() }
        return when {
            digitsOnly.length <= 2 -> digitsOnly
            else -> "${digitsOnly.take(2)}/${digitsOnly.substring(2).take(2)}"
        }
    }

    fun isValidExpiryDate(input: String): Boolean {
        val parts = input.split("/")
        if (parts.size != 2) return false
        
        val month = parts[0].toIntOrNull() ?: return false
        val year = parts[1].toIntOrNull() ?: return false
        
        return month in 1..12 && year in 0..99
    }
} 