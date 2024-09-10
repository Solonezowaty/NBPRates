package com.solonezowaty.nbpratesapp.data

import com.solonezowaty.nbpratesapp.data.model.NbpRatesTableData
import retrofit2.http.GET
import retrofit2.http.Path

interface NbpRatesApi {
    /*
        This method require table type which is 'A', 'B' or 'C'.
        For purpose of this app we're passing only 'A' and 'B'
     */
    @GET("exchangerates/tables/{table}/")
    suspend fun getCurrentRatesTableType(
        @Path("table") tableType: String
    ): List<NbpRatesTableData>
}