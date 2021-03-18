package com.erbe.sunzoid.data.network.mapper

import com.erbe.sunzoid.data.network.model.ApiLocation
import com.erbe.sunzoid.data.network.model.ApiLocationDetails
import com.erbe.sunzoid.domain.model.Location
import com.erbe.sunzoid.domain.model.LocationDetails

interface ApiMapper {

    fun mapApiLocationToDomain(apiLocation: ApiLocation): Location

    fun mapApiLocationDetailsToDomain(apiLocationDetails: ApiLocationDetails): LocationDetails
}