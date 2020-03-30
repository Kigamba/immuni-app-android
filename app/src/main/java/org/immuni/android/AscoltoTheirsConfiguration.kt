package org.immuni.android

import org.immuni.android.api.oracle.CustomOracleAPI
import org.immuni.android.api.oracle.model.AscoltoMe
import org.immuni.android.api.oracle.model.AscoltoSettings
import org.immuni.android.api.oracle.model.FcmTokenRequest
import com.bendingspoons.concierge.ConciergeManager
import com.bendingspoons.oracle.Oracle
import com.bendingspoons.oracle.api.model.DevicesRequest
import com.bendingspoons.pico.Pico
import com.bendingspoons.theirs.TheirsConfiguration
import com.bendingspoons.theirs.adjust.AdjustAppSecret
import com.bendingspoons.theirs.adjust.AdjustConfiguration
import com.bendingspoons.theirs.adjust.AdjustEnvironment
import com.bendingspoons.theirs.fcm.FirebaseFCMConfiguration
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.KoinComponent
import org.koin.core.inject

class AscoltoTheirsConfiguration: TheirsConfiguration, KoinComponent {
    override fun adjustConfig() = object: AdjustConfiguration {
        override val appToken = "xyz"
        override val appSecret = AdjustAppSecret(3, 1, 2, 3, 4)
        override val environment = if (BuildConfig.DEBUG || BuildConfig.FLAVOR != "production") AdjustEnvironment.SANDBOX else AdjustEnvironment.PRODUCTION
        override val concierge: ConciergeManager by inject()
        override val pico: Pico by inject()
    }
    override fun firebaseFCMConfig() = object: FirebaseFCMConfiguration {
        override val concierge: ConciergeManager by inject()

        override suspend fun onNewPushNotification(remoteMessage: RemoteMessage) {
            // Check if message contains a data payload.
            //toast("DATA ${remoteMessage.data}.")

            // Check if message contains a notification payload.
            //remoteMessage.notification?.let {
            //    toast("NOTIFICATION ${it.body}")
            //}
        }

        override suspend fun onNewToken(token: String) {
            val oracle: Oracle<AscoltoSettings, AscoltoMe> by inject()

            // be sure to call settings before
            oracle.api.devices(DevicesRequest(
                uniqueId = concierge.backupPersistentId.id
            ))

            oracle.customServiceAPI(CustomOracleAPI::class).fcmNotificationToken(FcmTokenRequest(
                token = token
            ))
        }
    }
}