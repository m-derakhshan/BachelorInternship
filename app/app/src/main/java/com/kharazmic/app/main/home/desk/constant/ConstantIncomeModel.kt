package com.kharazmic.app.main.home.desk.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ConstantIncomeModel(
    val id: String = "",// ای دی که شما توی پایگاه داده به هر صندوق اختصاص دادید
    val name: String = "",// نام فارسی هر صندوق
    val riskCriteriaBeta: String = "",//معیار ریسک بتا
    val riskCriteriaAlpha: String = "",// معیار ریسک آلفا
    val manager: String = "",// اسم مدیر صندوق
    val guarantor: String = "",// ضامن نقد شوندگی
    val investment_manager: String = "",//مدیران سرمایه گذاری
    val supervisor: String = "",//متولی صندوق
    val type: String = "",//نوع و اندازه صندوق
    val start_date: String = "",//تاریخ آغاز فعالیت:
    val accounting: String = "",//حسابرسی
    val address: String = "",//آدرس سایت(تماس با صندوق):
    val update_date: String = "",//بروزرسانی
    val price_per_unit: String = "",//قیمت صدور هر واحد:
    val amount_invest_unit: String = "",//تعداد واحد های سرمایه گذاری:
    val net_worth: String = "",//کل خالص ارزش دارایی ها(ریال)
    val cancel_price: String = "",//قیمت ابطال هر واحد(ریال):
    val number_of_investor2: String = "",//تعداد سرمایه گذاران حقوقی:
    val percentage_investor2: String = "",//درصد تملک:(برای حقوقی منظوره)
    val statistic_price_per_unit: String = "",//قیمت آماری هر واحد(ریال):
    val stock: Double = 0.0,// میزان سهام در قسمت ترکیب دارایی صندوق
    val bank: Double = 0.0,// میزان سپرده بانکی در قسمت ترکیب دارایی صندوق
    val cash: Double = 0.0,// میزان وجه نقد در قسمت ترکیب دارایی صندوق
    val other: Double = 0.0,// میزان سایر دارایی ها در قسمت ترکیب دارایی صندوق
    val shared: Double = 0.0,// میزان اوراق مشارکت در قسمت ترکیب دارایی صندوق
    val most_weight: Double = 0.0,// میزان ۵ سهم با ارزش در قسمت ترکیب دارایی صندوق
    val guaranteed_profit: String = "",//نرخ سود تضمین شده:
    val one_month: String = "",//بازدهی ۱ ماهه:
    val predicted: String = "",//نرخ سود پیش بینی شده:
    val three_month: String = "",//بازدهی ۳ ماهه:
    val divide: String = "",//دوره های تقسیم سود:
    val six_month: String = "",//بازدهی ۶ ماهه:
    val annual: String = "",//بازدهی یک ساله:
    val total_profit: String = "",//میزان سود از تاریخ تاسیس تا کنون:
    val number_investor1: String = "",//تعداد سرمایه گذاران حقیقی:
    val percentage_investor1: String = ""//درصد تملک:(برای حقیقی منظوره)
) : Parcelable