/** Weather from Open Weather and Weather dot Gov
 *
 *  Copyright 2024 Eric Albright - Kokopelli Home Automation - 
 *                 - most of this is copied from research sources and other operating programs as I learn
 *                   so Copied. Right.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

import groovy.transform.Field
import java.text.SimpleDateFormat

@Field static final String appVersionFLD  = '3.0.3'
@Field static final String appModifiedFLD = '2024-01-01'
@Field static final String appDescriptionFLD = 'Outside Weather'


definition(
    name:"Outside Weather",
    namespace: "ERA6515",
    author: "Eric Albright",
    description: "Making the Weather.gov data usable with Hubitat.",
    category: "Convenience",
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
    importUrl: "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/OutsideWeather/Outside%20Weather.groovy",
)


preferences {
	page(name: "startPage")
	page(name: "mainPage")
    page(name: "WeatherLookupPage")
    // TODO next page is frequency of runing and create CRON jobs
    page(name: "usingWeatherDotGov")
    page(name: "usingOpenWeather")
    page(name: "cfgVarSaving")
    page(name: "settingsPage")
    page(name: "changeLogPage")
}

def startPage() {
    checkVersionData(true)
    if (state.UseGov == null) { state.UseGov = sFALSE }
    if (state.UseOpen == null) { state.UseOpen = sFALSE }
    if (!state.UseOpen && !state.UseGov) { state.appInstalled = 'INCOMPLETE' }
    return mainPage() 

}

def mainPage() {
    return dynamicPage(name: "mainPage", uninstall: false, install: true) {
        appInfoSect()
        section(sectHead("Manage Logging, and Metrics")) {
            href "changeLogPage", title: inTS1("History", sSETTINGS), description: inputFooter(sTTRH, sCLRGRY, true)
            href "settingsPage", title: inTS1("Logging", sSETTINGS), description: inputFooter(sTTM, sCLRGRY, true)
        }			
        section(sectHead("Enter location information")) {
				href "WeatherLookupPage", title: inTS1("Basic Weather dot gov information", "0"), description: inputFooter(sTTM, sCLRGRY, true) 
        }     
        section(sectHead("Enter how to save wanted information:")) {
				href "cfgVarSaving", title: inTS1("Where to save information that is needed", "checkMarkGreen2"), description: inputFooter(sTTM, sCLRGRY, true)
        savClo = null
        state.remove("savClo")
	    app.removeSetting("savClo")
        savCloSource = null
        state.remove("savCloSource")
	    app.removeSetting("savCloSource")  
        savDew = null
        state.remove("savDew")
	    app.removeSetting("savDew")  
        savHeatI = null
        state.remove("savHeatI")
	    app.removeSetting("savHeatI")  
        savHumi = null
        state.remove("savHumi")
	    app.removeSetting("savHumi")  
        savPres = null
        state.remove("savPres")
	    app.removeSetting("savPres")  
        savTemp = null
        state.remove("savTemp")
	    app.removeSetting("savTemp")  
        savTempSource = null
        state.remove("savTempSource")
	    app.removeSetting("savTempSource")  
        savTempVar = null
        state.remove("savTempVar")
	    app.removeSetting("savTempVar")  
        savWindC = null
        state.remove("savWindC")
	    app.removeSetting("savWindC")  
        savWindD = null
        state.remove("savWindD")
	    app.removeSetting("savWindD")  
        savWindG = null
        state.remove("savWindG")
	    app.removeSetting("savWindG")  
        savWindS = null
        state.remove("savWindS")
	    app.removeSetting("savWindS")         
        savCloVar = null
        state.remove("savCloVar")
	    app.removeSetting("savCloVar")  
        savDewSource = null
        state.remove("savDewSource")
	    app.removeSetting("savDewSource")  
        savDewVar = null
        state.remove("savDewVar")
	    app.removeSetting("savDewVar")  
                    savHumiSource = null
        state.remove("savHumiSource")
	    app.removeSetting("savHumiSource")  
        savHumiVar = null
        state.remove("savHumiVar")
	    app.removeSetting("savHumiVar") 
			}             
        appFooter()
    }
}
                    
  
                  


def settingsPage() {
    return dynamicPage(name: "settingsPage", uninstall: false, install: false) {
        section(sectHead("Logging:")) {
            input "logInfo", sBOOL, title: inTS1("Show Info Logs?", sDEBUG), required: false, defaultValue: true, submitOnChange: true
            input "logWarn", sBOOL, title: inTS1("Show Warning Logs?", sDEBUG), required: false, defaultValue: true, submitOnChange: true
            input "logError", sBOOL, title: inTS1("Show Error Logs?", sDEBUG), required: false, defaultValue: true, submitOnChange: true
            input "logDebug", sBOOL, title: inTS1("Show Debug Logs?", sDEBUG), description: "Auto disables after 6 hours", required: false, defaultValue: false, submitOnChange: true
            input "logTrace", sBOOL, title: inTS1("Show Detailed Logs?", sDEBUG), description: "Only enabled when asked to.\n(Auto disables after 6 hours)", required: false, defaultValue: false, submitOnChange: true
        }
    }
}

def changeLogPage() {
    return dynamicPage(name: "changeLogPage", uninstall: false, install: false) {
        section(sectHead("History:")) {
            txt = " History:\n"
            txt += "   1.0.1 \n"
            txt += "       Starting creation with new layouts... 23-12-20 \n"
            txt += "\n"            
            paragraph "${txt}"

            txt = "   1.0.2 \n"
            txt += "       Using Weather dot Gov data... 23-12-20 \n"
            txt += "\n"            
            paragraph "${txt}"

            txt = "   2.0.1 \n"
            txt += "       Added Open Weather data... 23-12-27 \n"
            txt += "\n"    
                        
            txt = "   3.0.1 \n"
            txt += "       Modified to array storage ... 23-12-29 \n"
            txt += "\n"      
                        
            txt = "   3.0.2 \n"
            txt += "       added variable storage ... 23-12-30 \n"
            txt += "\n"   
                        
            txt = "   3.0.3 \n"
            txt += "       added forcast data ... inprogress \n"
            txt += "\n"
            
            paragraph "${txt}"
        }
        // using this for cleanup as I develop...
/*
state.Temperature = null
        state.remove("Temperature")
	    app.removeSetting("Temperature")  
*/
    }
}

def WeatherLookupPage() {
    return dynamicPage(name: "WeatherLookupPage", uninstall: false, install: false) {
    
        section(sectHead("Location Options:")) {
            
        tStr = htKok
        tStr += "<br><table style='border: 1px solid ${sCLRGRY};border-collapse: collapse;'>"
            if (state.UseGov) {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sGovWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sUsed)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sGovWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sNotUsed)+"</td></tr>"
            }
            if (state.UseOpen) {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sOpenWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sUsed)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sOpenWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sNotUsed)+"</td></tr>"
            }
        tStr += "</table>"
        tStr = spanSm(tStr, sCLRGRY)     
        section(sectH3TS((String)app?.name, tStr, getAppImg("logo"), sCLRKOK)) {
        }
            
              input "lat", sTEXT, title: inTS1("Latitude", "checkMarkGreen2"), description: "enter latitude of the location", defaultValue: "${location.latitude}", multiple: false, required: true, submitOnChange: true
              input "lng", sTEXT, title: inTS1("Longitude", "checkMarkGreen2"), description: "enter zip code of the location", defaultValue: "${location.longitude}", multiple: false, required: true, submitOnChange: true
              input "unitFormat", sENUM, title: inTS1("Display format", "checkMarkGreen2"), required: true, options: lImperial, submitOnChange:true
              if(!lat || !lng || !unitFormat) state.appInstalled = "INCOMPLETE"
     }
        section(sectHead("If you are using Weather dot gov, fill in this section:","34")) {
            if (state.UseGov) {
                href "usingWeatherDotGov", title: inTS1("Information needed for Weather Dot Gov", "options-green"), description: inputFooter(sTTM, sCLRGRY, true)
            } else {
                href "usingWeatherDotGov", title: inTS1("Information needed for Weather Dot Gov", "options-red"), description: inputFooter(sTTM, sCLRGRY, true)
            }
        }
        section(sectHead("If you are using Open Weather, fill in this section:","28")) {
            if (state.UseOpen) {
                href "usingOpenWeather",  title: inTS1("Information needed for Open Weather", "options-green"), description: inputFooter(sTTM, sCLRGRY, true)
            } else {
                href "usingOpenWeather",  title: inTS1("Information needed for Open Weather", "options-red"), description: inputFooter(sTTM, sCLRGRY, true)
            }
        
            tStr = "Weather is retrieved first from Weather dot Gov then Open Weather.\n"
            tStr +="After attempts to retrieve the information is complete the information is saved in the global varibiales\n"
            tStr +="If the primary source of data is not avaliable the other source is used unless it is also not available.\n"

            tStr = spanSm(tStr, sCLRGRY)
            paragraph sectH3TS("So you understand", tStr, null, sCLRKOK)
        }
//        }
            
    }         
}

def usingWeatherDotGov() {
    return dynamicPage(name: "usingWeatherDotGov", uninstall: false, install: false) {

        section(sectHead("Weather dot Gov Options:","34")) {
            logTrace ("usingWeatherDotGov")
            
        tStr = htKok
        tStr += "<br><table style='border: 1px solid ${sCLRGRY};border-collapse: collapse;'>"
            if (state.UseGov) {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sGovWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sUsed)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sGovWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sNotUsed)+"</td></tr>"
            }
            if(station){
               tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sCode)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sInstalled)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sCode)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sMissing)+"</td></tr>"
            }
        tStr += "</table>"
        tStr = spanSm(tStr, sCLRGRY)     

        section(sectH3TS((String)app?.name, tStr, getAppImg("logo"), sCLRKOK)) {
        }
        paragraph htmlLine()

            paragraph "If this is not completely filled out weather dot gov will NOT be used.\n"
            paragraph "Station ID can be found be visitng the <a href='http://weather.gov' target='_blank'>Weather.gov</a> homepage and putting in your zipcode or City, ST to find the ID (ie. KADS)"
            input "station", sTEXT, title: inTS1("Station ID (UPPERCASE)", sSETTINGS), description: "enter station of the location", multiple: false, required: false, submitOnChange: true
            if (station) {
                state.UseGov = sFALSE
                getGovWeatherData()
            } else {
                state.UseGov = sFALSE
            }
            if (!state.UseGov && !UseOpen) { state.appInstalled = 'INCOMPLETE' }
        }
    }

}

def usingOpenWeather() {
    return dynamicPage(name: "usingOpenWeather", uninstall: false, install: false) {

        section(sectHead("Open Weather Options:","28")) {
            logTrace ("usingOpenWeather")
            
        tStr = htKok
        tStr += "<br><table style='border: 1px solid ${sCLRGRY};border-collapse: collapse;'>"
            if (state.UseOpen) {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sOpenWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sUsed)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sOpenWeather)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sNotUsed)+"</td></tr>"
            }
            if(APIKey){
               tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sAPI)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sInstalled)+"</td></tr>"
            } else {
                tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBld(sAPI)+"</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>"+spanSmBr(sMissing)+"</td></tr>"
            }
        tStr += "</table>"
        tStr = spanSm(tStr, sCLRGRY)     

        section(sectH3TS((String)app?.name, tStr, getAppImg("logo"), sCLRKOK)) {
        }
        paragraph htmlLine()

            paragraph "If this is not completely filled out open weather will NOT be used.\n"
            
            paragraph "Open Weather requires an API Key <a href='https://openweathermap.org/api' target='_blank'>Open Weather </a> to apply for the key"
            input "APIKey", "password", title: "OpenWeather.org API Key", submitOnChange:true, required:false
            if (APIKey) {
                state.UseOpen = sFALSE
                getOpenWeatherData()
            } else { state.UseOpen = sFALSE }
            if (!state.UseOpen && !state.UseGov) { state.appInstalled = 'INCOMPLETE' }
        }
    }

}

def cfgVarSaving() {
    return dynamicPage(name: "cfgVarSaving", uninstall: false, install: false) {
    section(sectHead("What to Save:")) {
             
    lWeatherPoints.each {x ->
        switch("${x}") {
            case "Temperature":
                input "savTemp", sBOOL, title: inTS1("Would you like to save Temperature to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savTemp) {
                input "savTempSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, options: lWeatherSources, width: 4
                input "savTempVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savTempSource && savTempVar) {
                if (state.Temperature == null) state.Temperature = ["primary": savTempSource, "globalVar": savTempVar] else state.Temperature += ["primary": savTempSource, "globalVar": savTempVar]                   
                }
                }
                paragraph htmlLine()
                break
            case "Humidity":
                input "savHumi", sBOOL, title: inTS1("Would you like to save Humidity to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savHumi) {
                input "savHumiSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savHumiVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savHumiSource && savHumiVar) {
                if (state.Humidity == null) state.Humidity = ["primary": savHumiSource, "globalVar": savHumiVar] else state.Humidity += ["primary": savHumiSource, "globalVar": savHumiVar]                   
                }
                }
                paragraph htmlLine()
                 break
            case "Dew Point":
                if (state.Dewpoint == null) break
                input "savDew", sBOOL, title: inTS1("Would you like to save Dew Point to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savDew) {
                input "savDewSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savDewVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savDewSource && savDewVar) {
                if (state.Dewpoint == null) state.Dewpoint = ["primary": savDewSource, "globalVar": savDewVar] else state.Dewpoint += ["primary": savDewSource, "globalVar": savDewVar]                   
                }
                }
                paragraph htmlLine()
                 break
            case "Wind Speed":
                input "savWindS", sBOOL, title: inTS1("Would you like to save Wind Speed to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savWindS) {
                input "savWindSSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savWindSVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savWindSSource && savWindSVar) {
                if (state.WindSpeed == null) state.WindSpeed = ["primary": savWindSSource, "globalVar": savWindSVar] else state.WindSpeed += ["primary": savWindSSource, "globalVar": savWindSVar]                   
                }
                }
                paragraph htmlLine()
                 break            //WindSpeed
            case "Wind Gust":
                input "savWindG", sBOOL, title: inTS1("Would you like to save Wind Gust to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savWindG) {
                input "savWindGSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savWindGVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savWindGSource && savWindGVar) {
                if (state.WindGust == null) state.WindGust = ["primary": savWindGSource, "globalVar": savWindGVar] else state.WindGust += ["primary": savWindGSource, "globalVar": savWindGVar]                   
                }
                }
                paragraph htmlLine()
                 break             //WindGust
            case "Wind Direction":
                input "savWindD", sBOOL, title: inTS1("Would you like to save Wind Direction to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savWindD) {
                input "savWindDSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savWindDVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savWindDSource && savWindDVar) {
                if (state.WindDirection == null) state.WindDirection = ["primary": savWindDSource, "globalVar": savWindDVar] else state.WindDirection += ["primary": savWindDSource, "globalVar": savWindDVar]                   
                }
                }
                paragraph htmlLine()
                 break              //WindDirection
            case "Visibility":
                input "savVis", sBOOL, title: inTS1("Would you like to save Visibility to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savVis) {
                input "savVisSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savVisVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savVisSource && savVisVar) {
                if (state.Visibility == null) state.Visibility = ["primary": savVisSource, "globalVar": savPresVar] else state.Visibility += ["primary": savVisSource, "globalVar": savPresVar]                   
                }
                }
                paragraph htmlLine()
                 break             //Visibility
            case "Pressure":
                input "savPres", sBOOL, title: inTS1("Would you like to save Pressure to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savPres) {
                input "savPresSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savPresVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savPresSource && savPresVar) {
                if (state.Pressure == null) state.Pressure = ["primary": savTempSource, "globalVar": savTempVar] else state.Pressure += ["primary": savTempSource, "globalVar": savTempVar]                   
                }
                }
                paragraph htmlLine()
                 break            //Pressure
            case "Clouds":
                input "savClo", sBOOL, title: inTS1("Would you like to save Clouds to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savClo) {
                input "savCloSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savCloVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savCloSource && savCloVar) {
                if (state.Clouds == null) state.Clouds = ["primary": savCloSource, "globalVar": savCloVar] else state.Clouds += ["primary": savCloSource, "globalVar": savCloVar]                   
                }
                }
                paragraph htmlLine()
                 break            //Clouds
            case "Wind Chill":
                input "savWindC", sBOOL, title: inTS1("Would you like to save Wind Chill to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savWindC) {
                input "savWindCSource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savWindCVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (savWindCSource && savWindCVar) {
                if (state.WindChill == null) state.WindChill = ["primary": savWindCSource, "globalVar": savWindCVar] else state.WindChill += ["primary": savWindCSource, "globalVar": savWindCVar]                   
                }
                }
                paragraph htmlLine()
                 break 
            case "Heat Index":
                input "savHeatI", sBOOL, title: inTS1("Would you like to save Heat Index to a global variaible?", sDEBUG), required: false, defaultValue: false, submitOnChange: true
                if (savHeatI) {
                input "savHeatISource", sENUM, title: inTS1("Which weather source would you like to use as primary?", sDEBUG), required: true, submitOnChange: true, width: 4, options: lWeatherSources
                input "savHeatIVar", "enum", title: inTS1("Which varibile would you like to save to?", sDEBUG), submitOnChange: true, width: 4, style: 'margin-left:10px', options: getAllGlobalVars().findAll{it.value.type == "bigdecimal"}.keySet().collect().sort{it.capitalize()}
                    if (saveHeatISource && savHeatIVar) {
                    if (state.HeatIndex == null) state.HeatIndex = ["primary": savHeatISource, "globalVar": savHeatIVar] else state.HeatIndex += ["primary": savHeatISource, "globalVar": savHeatIVar]                   
                }
                }
                paragraph htmlLine()
                 break
        }
    }
            tStr = "Prior to selecting the global variabiles you must create them.\n"
            tStr +="They are created in the hub by going to settings and then hub variabiles.\n\n"
            tStr += "If the primary source information is not available the secondary source will used prior to it being listed as n/a.\n"

            tStr = spanSm(tStr, sCLRGRY)
            paragraph sectH3TS("So you understand", tStr, null, sCLRKOK)
    }
    }
}
    


def installed() {
    logDebug ("Installed with settings: ${settings}")
    initialize()
}

def updated() {
    logDebug ("Updated with settings: ${settings}")
    unschedule()
    initialize()
}

def initialize() {
    if (state.UseGov && !state.UseOpen) schedule('53 14/15 * * * ?',getGovWeatherData)
}

//************************************************
//                STATIC VARIABLES               *
//              Apperance Variables              *
//************************************************

@Field static final String sNULL          = (String)null
@Field static final String sBLANK         = ''
@Field static final String sSPACE         = ' '
@Field static final String sBOOL          = 'bool'
@Field static final String sDEBUG         = 'blank'
@Field static final String sLINEBR        = '<br>'
@Field static final String sSMALL         = 'small'
@Field static final String sFALSE         = 'false'
@Field static final String sTRUE          = 'true'
@Field static final String sNUMBER        = 'number'
@Field static final String sTEXT          = 'text'
@Field static final String sENUM          = 'enum'
@Field static final String sCLR4D9        = '#2784D9'
@Field static final String sCLRKOK        = '#00bbbb'
@Field static final String sCLRGRY        = 'gray'
@Field static final String sCLRRED        = 'red'
@Field static final String sCLRRED2       = '#cc2d3b'
@Field static final String sCLRGRN        = 'green'
@Field static final String sCLRGRN2       = '#43d843'
@Field static final String sCLRORG        = 'darkorange'
@Field static final List<String> lImperial = ["Imperial", "Metric"]
@Field static final List<String> lWeatherSources = ["Weather dot Gov","Open Weather"]
@Field static final List<String> lWeatherPoints  = ["Temperature", "Humidity", "Dew Point", "Wind Speed", "Wind Direction", "Wind Gust", "Clouds", "Pressure", "Visablility", "Wind Chill", "Heat Index"]
@Field static final String sSETTINGS      = 'reports'
@Field static final String sTTM           = 'Tap to modify...'
@Field static final String sTTRH          = 'Tap to retrieve history...'
@Field static final String htKok           = 'Kokopelli Home Automation'
@Field static final String sAPPJSON       = 'application/json'
@Field static final String sAPI            = 'API Key'
@Field static final String sMissing        = 'Missing'
@Field static final String sInstalled      = 'Installed'
@Field static final String sOpenWeather    = 'Open Weather'
@Field static final String sGovWeather    = 'Weather Dot Gov'
@Field static final String sUsed           = 'Used'
@Field static final String sNotUsed        = 'Not Used'
@Field static final String sCode           = 'Station'
@Field static final String sZip            = 'Zip Code'
static String appVersion()  { return appVersionFLD }
static String appVersionDt()  { return appModifiedFLD }
static String appName() {return appDescriptionFLD }

// Root HTML Objects
static String span(String str, String clr=sNULL, String sz=sNULL, Boolean bld=false, Boolean br=false) { return str ? "<span ${(clr || sz || bld) ? "style='${clr ? "color: ${clr};" : sBLANK}${sz ? "font-size: ${sz};" : sBLANK}${bld ? "font-weight: bold;" : sBLANK}'" : sBLANK}>${str}</span>${br ? sLINEBR : sBLANK}" : sBLANK }
static String div(String str, String clr=sNULL, String sz=sNULL, Boolean bld=false, Boolean br=false) { return str ? "<div ${(clr || sz || bld) ? "style='${clr ? "color: ${clr};" : sBLANK}${sz ? "font-size: ${sz};" : sBLANK}${bld ? "font-weight: bold;" : sBLANK}'" : sBLANK}>${str}</div>${br ? sLINEBR : sBLANK}" : sBLANK }
static String spanImgStr(String img=sNULL) { return img ? span("<img src='${(!img.startsWith("http://") && !img.startsWith("https://")) ? getAppImg(img) : img}' width='42'> ") : sBLANK }
static String inputFooter(String str, String clr=sCLRKOK, Boolean noBr=false) { return str ? lineBr(!noBr) + divSmBld(str, clr) : sBLANK }
static String lineBr(Boolean show=true) { return show ? sLINEBR : sBLANK }
static String getAppImg(String imgName) { return "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/Images/${imgName}.png" }
static String sectHead(String str, String img = sNULL) { return str ? "<h3 style='margin-top:0;margin-bottom:0;'>" + spanImgStr(img) + span(str, sCLRORG, sNULL, true) + "</h3>" + "<hr style='background-color:${sCLRGRY};font-style:italic;height:1px;border:0;margin-top:0;margin-bottom:0;'>" : sBLANK }
static String sectH3TS(String t, String st, String i = sNULL, String c=sCLRKOK) { return """<h3 style="color:${c};font-weight: bold">${i ? """<img src="${i}" width="42"> """ : sBLANK} ${t?.replaceAll("\\n", "<br>")}</h3>${st ?: sBLANK}""" }
static String sectH1TS(String t, String st, String i = sNULL, String c=sCLRKOK) { return """<h1 style="color:${c};font-weight: bold">${i ? """<img src="${i}" width="42"> """ : sBLANK} ${t?.replaceAll("\\n", "<br>")}</h1>${st ?: sBLANK}""" }
static String inTS1(String str, String img = sNULL, String clr=sNULL, Boolean und=true) { return spanSmBldUnd(str, clr, img) }
static String actFoot(String str) { return str ? inputFooter(str, sCLRKOK, false) : sBLANK }
static String htmlLine(String color=sCLRKOK) { return "<hr style='background-color:${color};height:1px;border:0;margin-top:0;margin-bottom:0;'>" }
static String spanSmBld(String str, String clr=sNULL, String img=sNULL)    { return str ? spanImgStr(img) + span(str, clr, sSMALL, true)           : sBLANK }
static String spanSmBldUnd(String str, String clr=sNULL, String img=sNULL) { return str ? spanImgStr(img) + span(strUnder(str), clr, sSMALL, true) : sBLANK }
static String spanSmBr(String str, String clr=sNULL, String img=sNULL)     { return str ? spanImgStr(img) + span(str, clr, sSMALL, false, true)    : sBLANK }
static String spanSm(String str, String clr=sNULL, String img=sNULL)       { return str ? spanImgStr(img) + span(str, clr, sSMALL)                 : sBLANK }
static String strUnder(String str, Boolean showUnd=true) { return str ? (showUnd ? "<u>${str}</u>" : str) : sBLANK }
static String divSmBld(String str, String clr=sNULL, String img=sNULL)      { return str ? div(spanImgStr(img) + span(str), clr, sSMALL, true)        : sBLANK }

def appFooter() {
    section() {
        paragraph htmlLine()
		paragraph inputFooter("Kokopelli Home Automation")
		paragraph actFoot("${appModifiedFLD} - ${appVersionFLD}")
        if (state.footerMessage) paragraph actFoot ("$state.footerMessage")
    }

}

def appInfoSect() {
    String tStr = htKok
    Boolean isNote = false
    List<Map> verMap = []
    verMap.push([name: "App:", ver: "v${appVersionFLD}"])
     if(verMap?.size()) {
        tStr += "<table style='border: 1px solid ${sCLRGRY};border-collapse: collapse;'>"
        verMap.each { it->
            tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBld((String)it.name)}</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBr("${(String)it.ver}")}</td></tr>"
        }
         tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBld('Weather dot Gov')}</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBr("${state.UseGov}")}</td></tr>"
         tStr += "<tr style='border: 1px solid ${sCLRGRY};'><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBld('Open Weather')}</td><td style='border: 1px solid ${sCLRGRY};padding: 0px 3px 0px 3px;'>${spanSmBr("${state.UseOpen}")}</td></tr>"
         tStr += "</table>"
      }
     tStr = spanSm(tStr, sCLRGRY)     

    section(sectH3TS((String)app?.name, tStr, getAppImg("logo"), sCLRKOK)) {
        if(!state.appInstalled == 'COMPLETE') {
            paragraph spanSmBld("--NEW Install--", sCLRKOK)
        } else {
            if(!isNote) { paragraph inputFooter("No Issues to Report", sCLRGRY, true) }
        }
        paragraph htmlLine()
    }
}

/******************************************
|           Is it all upto date           |
******************************************/
void checkVersionData(Boolean now = false) { //This reads a JSON file from GitHub with version numbers
    Integer lastUpd = getLastTsValSecs("lastAppDataUpdDt")
    if (now || !state.appData || (lastUpd > (3600*6))) {
        if(now && (lastUpd < 300)) { return }
        getConfigData()
        getNoticeData()
    }
}
    
void checkGov() {

}

void getConfigData() {
    Map params = [
        uri: "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/appData.json",
        contentType: sAPPJSON,
        timeout: 20
    ]
    Map data = (Map)getWebData(params, "appData", false)  
    if(data) {
        state.appData = data
        logDebug("Successfully Retrieved (v${data?.appDataVer}) of AppData Content from GitHub Repo...")
// TODO figure out how to get ver data so I can flag if running beta program
//        chk = "data.applications.${appDescriptionFLD}?.ver"
//        logDebug(" ... $chk")
    }
    Map hfparams = [
        uri: "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/headfoot.json",
        contentType: sAPPJSON,
        timeout: 20
    ]
    Map hfdata = (Map)getWebData(hfparams, "headfoot", false)  
    if(hfdata) {
            state.headerMessage = hfdata.headerMessage
            state.footerMessage = hfdata.footerMessage
        logDebug("Successfully Retrieved headfoot Content from GitHub Repo...")
    }
}

void getNoticeData() {
    Map params = [
        uri: "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/notices.json",
        contentType: sAPPJSON,
        timeout: 20
    ]
    Map data = (Map)getWebData(params, "noticeData", false)
    if(data) {
        state.noticeData = data
        logDebug("Successfully Retrieved Developer Notices from GitHub Repo...")
    }
}

private getWebData(Map params, String desc, Boolean text=true) {
    try {
        logTrace("getWebData: ${desc} data")
        httpGet(params) { resp ->
            if(resp?.status != 200) logWarn("${resp?.status} $params")
            if(resp?.data) {
                if(text) { return resp.data.text?.toString() }
                return resp.data
            }
        }
    } catch (ex) {
        if(ex instanceof groovyx.net.http.HttpResponseException) {
            logWarn("${desc} file not found")
        } else { logError("getWebData(params: $params, desc: $desc, text: $text) Exception: ${ex}", false, ex) }
        return "${desc} info not found"
    }
}

private String getTsVal(String key) {
    String appId=app.getId()
    return sNULL
}

Integer getLastTsValSecs(String val, Integer nullVal=1000000) {
        return (val && getTsVal(val)) ? GetTimeDiffSeconds(getTsVal(val)).toInteger() : nullVal
}

private void updTsVal(String key, String dt=sNULL) {
    String val = dt ?: getDtNow()
    if(key in svdTSValsFLD) { updServerItem(key, val); return }

    String appId=app.getId()
    Map data=tsDtMapFLD[appId] ?: [:]
    if(key) data[key]=val
    tsDtMapFLD[appId]=data
    tsDtMapFLD=tsDtMapFLD
}

Long GetTimeDiffSeconds(String lastDate, String sender=sNULL) {
    try {
        if(lastDate?.contains("dtNow")) { return 10000 }
        Date lastDt = Date.parse("E MMM dd HH:mm:ss z yyyy", lastDate)
        Long start = lastDt.getTime()
        Long stop = wnow()
        Long diff = (Long)((stop - start) / 1000L)
        return diff.abs()
    } catch (ex) {
        logError("GetTimeDiffSeconds Exception: (${sender ? "$sender | " : sBLANK}lastDate: $lastDate): ${ex}", false, ex)
        return 10000L
    }
}

static String getDtNow() {
    Date now = new Date()
    return formatDt(now)
}

static String formatDt(Date dt, Boolean tzChg=true) {
    def tf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")
    if(tzChg) { if(mTZ()) { tf.setTimeZone(mTZ()) } }
    return (String)tf.format(dt)
}
private static TimeZone mTZ(){ return TimeZone.getDefault() } // (TimeZone)location.timeZone

/******************************************
    the important stuff to run this app
******************************************/
/*****************************************
|     first part is for Weather.gov      |
*****************************************/
def getGovWeatherData() {
    logTrace ("In getGovWeatherData")
    displayFormat = unitFormat
    forecastURL = "https://api.weather.gov/stations/${station}/observations/latest"
    
	logDebug ("In getGovWeatherData - forecastURL: ${forecastURL}")
	def requestParams =
		[
			uri: forecastURL,
            requestContentType: sAPPJSON,
			contentType: sAPPJSON,
            timeout: 30,
		]
    
    Map data = (Map) getWebData (requestParams, "getGovWeatherData", false)
    
    if(data) {
        state.UseGov = sTRUE
        Map create = (Map)["Gov": 0.0, "Opn": 0.0]
        if (data.properties.temperature.value != null) {val = getTemperature (data.properties.temperature.value)} else {val = null}
            if (state.Temperature == null) state.Temperature = ["Gov": val] else state.Temperature += ["Gov": val]
        if (data.properties.relativeHumidity.value != null) {val = getRelativeHumidity (data.properties.relativeHumidity.value)} else {val = null}
            if (state.Humidity == null) state.Humidity = ["Gov": val] else state.Humidity += ["Gov": val]
        if (data.properties.dewpoint.value != null) {val = getTemperature (data.properties.dewpoint.value)} else {val = null}
             if (state.Dewpoint == null) state.Dewpoint = ["Gov": val] else state.Dewpoint += ["Gov": val]
        if (data.properties.windSpeed.value != null) {val = getWindSpeed (data.properties.windSpeed.value)} else {val = null}
            if (state.WindSpeed == null) state.WindSpeed = ["Gov": val] else state.WindSpeed += ["Gov": val]
        if (data.properties.windDirection.value != null) {val = getWindDirection (data.properties.windDirection.value)} else {val = null}
            if (state.WindDirection == null) state.WindDirection = ["Gov": val] else state.WindDirection += ["Gov": val]
        if (data.properties.windGust.value != null) {val = getWindSpeed (data.properties.windGust.value)} else {val = null}
            if (state.WindGust == null) state.WindGust = ["Gov": val] else state.WindGust += ["Gov": val]
        if (data.properties.barometricPressure.value != null) {val = getBarometric (data.properties.barometricPressure.value)} else {val = null}
            if (state.Pressure == null) state.Pressure = ["Gov": val] else state.Pressure += ["Gov": val]
        if (data.properties.seaLevelPressure.value != null) {val = getBarometric (data.properties.seaLevelPressure.value)} else {val = null}
            if (state.SeaPressure == null) state.SeaPressure = ["Gov": val] else state.SeaPressure += ["Gov": val]
        if (data.properties.visibility.value != null) {val = getVisibility (data.properties.visibility.value)} else {state.curVisibility = null}
            if (state.Visibility == null) state.Visibility = ["Gov": val] else state.Visibility += ["Gov": val]
        if (data.properties.maxTemperatureLast24Hours.value != null) {val = getTemperature (data.properties.maxTemperatureLast24Hours.value)} else {val = null}
            if (state.HighTemp == null) state.HighTemp = ["Gov": val] else state.HighTemp += ["Gov": val]
        if (data.properties.minTemperatureLast24Hours.value != null) {val = getTemperature (data.properties.minTemperatureLast24Hours.value)} else {val = null}
            if (state.LowTemp == null) state.LowTemp = ["Gov": val] else state.LowTemp += ["Gov": val]
        if (data.properties.precipitationLastHour.value != null) {val = getRain (data.properties.precipitationLastHour.value)} else {val = null}
            if (state.LastHourPrecip == null) state.LastHourPrecip = ["Gov": val] else state.LastHourPrecip += ["Gov": val]
        if (data.properties.precipitationLast3Hours.value != null) {val = getRain (data.properties.precipitationLast3Hours.value)} else {val = null}
            if (state.Hour3Precip == null) state.Hour3Precip = ["Gov": val] else state.Hour3Precip += ["Gov": val]
        if (data.properties.precipitationLast6Hours.value != null) {val = getRain (data.properties.precipitationLast6Hours.value)} else {val = null}
            if (state.Hour6Precip == null) state.Hour6Precip = ["Gov": val] else state.Hour6Precip += ["Gov": val]
        if (data.properties.cloudLayers.amount != null) {val = data.properties.cloudLayers.amount} else {val = null}
            if (state.Clouds == null) state.Clouds = ["Gov": val] else state.Clouds += ["Gov": val]
        if (data.properties.windChill.value != null) {val = getTemperature (data.properties.windChill.value)} else {val = null}
            if (state.WindChill == null) state.WindChill = ["Gov": val] else state.WindChill += ["Gov": val]
        if (data.properties.heatIndex.value != null) {val = getTemperature (data.properties.heatIndex.value)} else {val = null}    
            if (state.HeatIndex == null) state.HeatIndex = ["Gov": val] else state.HeatIndex += ["Gov": val] 

// use the next line to save the information for checking purposes
//        state.weatherData = data  // used for creation of the proceedure
// use the next 3 lines once you are done with crating the proceedure
//        state.weatherData = null                  
//        state.remove("weatherData")		 
//        app.removeSetting("weatherData")
        logDebug("Successfully Retrieved Weather Data from Weather dot gov...")
        getOpenWeatherData()
    }
}

def getOpenWeatherData() {
    logTrace ("In getOpenWeatherData")
    displayFormat = unitFormat
    station1 = station
//    forecastURL = "https://api.openweathermap.org/data/2.5/air_pollution?lat=${lat}&lon=${lng}&appid=${APIKey}"
forecastURL = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=${APIKey}"    
	logDebug ("In getOpenWeatherData - forecastURL: ${forecastURL}")
	def requestParams =
		[
			uri: forecastURL,
            requestContentType: sAPPJSON,
			contentType: sAPPJSON,
            timeout: 30,
		]

    Map data = (Map) getWebData (requestParams, "getOpenWeatherData", false)
    
    if(data) {
        state.UseOpen = sTRUE
        if (data.main.temp != null) {val = getKTemperature (data.main.temp)} else {val = null}
            if (state.Temperature == null) state.Temperature = ["Opn": val] else state.Temperature += ["Opn": val]      
        if (data.main.feels_like != null) {val = getKTemperature (data.main.feels_like)} else {val = null}
            if (state.WindChill == null) state.WindChill = ["Opn": val] else state.WindChill += ["Opn": val]     
            if (state.HeatIndex == null) state.HeatIndex = ["Opn": val] else state.HeatIndex += ["Opn": val]                                                                                                                   
        if (data.main.humidity != null) {val = getRelativeHumidity (data.main.humidity)} else {val = null}
            if (state.Humidity == null) state.Humidity = ["Opn": val] else state.Humidity += ["Opn": val]
        if (data.wind.speed != null) {val = getmpsWindSpeed (data.wind.speed)} else {val = null}
            if (state.WindSpeed == null) state.WindSpeed = ["Opn": val] else state.WindSpeed += ["Opn": val]
        if (data.wind.gust != null) {val = getmpsWindSpeed (data.wind.gust)} else {val = null}
            if (state.WindGust == null) state.WindGust = ["Opn": val] else state.WindGust += ["Opn": val]
        if (data.wind.deg != null) {val = getWindDirection (data.wind.deg)} else {val = null}
            if (state.WindDirection == null) state.WindDirection = ["Opn": val] else state.WindDirection += ["Opn": val]
        if (data.visibility != null) {val = getVisibility (data.visibility)} else {val = null}
            if (state.Visibility == null) state.Visibility = ["Opn": val] else state.Visibility += ["Opn": val]
        if (data.main.pressure != null) {val = gethPaPressure (data.main.pressure)} else {val = null}
            if (state.Pressure == null) state.Pressure = ["Opn": val] else state.Pressure += ["Opn": val]
        if (data.clouds.all != null) {val = data.clouds.all} else {val = null}
            if (state.Clouds == null) state.Clouds = ["Opn": val] else state.Clouds += ["Opn": val]
//        if (data.sys.sunrise != null) {val = formatDt(data.sys.sunrise)} else {val = null}
//            if (state.sunrise == null) state.sunrise = ["Opn": val] else state.sunrise += ["Opn": val]
                
// use the next line to save the information for checking purposes
//        state.weatherData1 = data
// use the next 3 lines once you are done with crating the proceedure
//        state.weatherData1 = null                  
//        state.remove("weatherData1")		 
//        app.removeSetting("weatherData1")
        
        logDebug("Successfully Retrieved Weather Data from OpenWeather...")
        saveNewInformation()
    }
}

private saveNewInformation() {
    logTrace ("saveNewInformation")
    lWeatherPoints.each {x ->
        switch("${x}") {
            case "Temperature":
                if (state.Temperature == null) break
                sPrim = state.Temperature.primary
                if (sPrim == null) break
                sLoc = state.Temperature.globalVar
                if (sLoc == null) break
                nOpn = state.Temperature.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Temperature.Gov
                if (nGov != null) nGov = nGov.toFloat()
                if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break
            case "Humidity":
                if (state.Humidity == null) break
                sPrim = state.Humidity.primary
                if (sPrim == null) break
                sLoc = state.Humidity.globalVar
                if (sLoc == null) break
                nOpn = state.Humidity.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Humidity.Gov
                if (nGov != null) nGov = nGov.toFloat()
            logDebug ("Humidity -> $sPrim , $sLoc , $nOpn , $nGov")
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break
            case "Dew Point":
                if (state.Dewpoint == null) break
                sPrim = state.Dewpoint.primary
                if (sPrim == null) break
                sLoc = state.Dewpoint.globalVar
                if (sLoc == null) break
                nOpn = state.Dewpoint.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Dewpoint.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break
            case "Wind Speed":
                if (state.WindSpeed == null) break
                sPrim = state.WindSpeed.primary
                if (sPrim == null) break
                sLoc = state.WindSpeed.globalVar
                if (sLoc == null) break
                nOpn = state.WindSpeed.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.WindSpeed.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break            //WindSpeed
            case "Wind Gust":
                if (state.WindGust == null) break
                sPrim = state.WindGust.primary
                if (sPrim == null) break
                sLoc = state.WindGust.globalVar
                if (sLoc == null) break
                nOpn = state.WindGust.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.WindGust.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break             //WindGust
            case "Wind Direction":
                if (state.WindDirection == null) break
                sPrim = state.WindDirection.primary
                if (sPrim == null) break
                sLoc = state.WindDirection.globalVar
                if (sLoc == null) break
                nOpn = state.WindDirection.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.WindDirection.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break              //WindDirection
            case "Visibility":
                if (state.Visibility == null) break
                sPrim = state.Visibility.primary
                if (sPrim == null) break
                sLoc = state.Visibility.globalVar
                if (sLoc == null) break
                nOpn = state.Visibility.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Visibility.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break             //Visibility
            case "Visibility":
                if (state.Pressure == null) break
                sPrim = state.Pressure.primary
                if (sPrim == null) break
                sLoc = state.Pressure.globalVar
                if (sLoc == null) break
                nOpn = state.Pressure.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Pressure.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break            //Pressure
            case "Clouds":
                if (state.Clouds == null) break
                sPrim = state.Clouds.primary
                if (sPrim == null) break
                sLoc = state.Clouds.globalVar
                if (sLoc == null) break
                nOpn = state.Clouds.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.Clouds.Gov
                if (nGov != null) nGov = nGov
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break            //Clouds
            case "Wind Chill":
                if (state.WindChill == null) break
                sPrim = state.WindChill.primary
                if (sPrim == null) break
                sLoc = state.WindChill.globalVar
                if (sLoc == null) break
                nOpn = state.WindChill.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.WindChill.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break 
            case "Heat Index":
                if (state.HeatIndex == null) break
                sPrim = state.HeatIndex.primary
                if (sPrim == null) break
                sLoc = state.HeatIndex.globalVar
                if (sLoc == null) break
                nOpn = state.HeatIndex.Opn
                if (nOpn != null) nOpn = nOpn.toFloat()
                nGov = state.HeatIndex.Gov
                if (nGov != null) nGov = nGov.toFloat()
            if (sLoc != null && sPrim == "Open Weather") { if(nOpn) setGlobalVar(sLoc,nOpn) } else {if(nGov) { setGlobalVar(sLoc,nGov) } else { setGlobalVar(sLoc,null)}}
                if (sLoc != null && sPrim == "Weather dot Gov") { if(nGov) setGlobalVar(sLoc,nGov) } else {if(nOpn) { setGlobalVar(sLoc,nOpn) } else { setGlobalVar(sLoc,null)}}
                break
        }
    }
}

private getTemperature (tempC) {
    logTrace ("getTemperture ${tempC}")
    if(displayFormat == "Imperial") {
        disValue = cTOf(tempC)
    } else { disValue = tempC.toFloat()}
    logDebug ("Display Temperature = $disValue")
    return (float) disValue.round(2)
}

private getKTemperature (tempK) {
    logTrace ("getTemperture ${tempK}")
    if(displayFormat == "Imperial") {
        disValue = kTOf(tempK)
    } else { disValue = kTOc(tempK)}
    logDebug ("Display Temperature = $disValue")
    return (float) disValue.round(2)
}
private getRelativeHumidity (humid) {
    logTrace ("getRelativeHumidity ${humid}")
    disValue = humid.toFloat()
    logDebug ("Display Humidity = $disValue")
    return (float) disValue.round(2)
}

private getWindDirection (windD) {
    logTrace ("getWindDirection ${windD}")
    disValue = windD.toFloat()
    logDebug ("Display Wind Direction = $disValue")
    return (float) disValue.round(0)
}      

private getmpsWindSpeed (wind) {
    logTrace ("getmpsWindSpeed ${wind}")
    if(displayFormat == "Imperial") {
        disValue = mpsTOmph(wind)
    } else { disValue = wind.toFloat()}
    logDebug ("Display Wind Speed  = $disValue")
    return (float) disValue.round(2)
} 

private getWindSpeed (wind) {
    logTrace ("getWindSpeed ${wind}")
    if(displayFormat == "Imperial") {
        disValue = kphTOmph(wind)
    } else { disValue = wind.toFloat()}
    logDebug ("Display Wind Speed  = $disValue")
    return (float) disValue.round(2)
}     

private gethPaPressure (pres) {
    logTrace ("gethPaPressure ${pres}")
    if(displayFormat == "Imperial") {
        disValue = hPaTOinhg(pres)
    } else { disValue = pres.toFloat()}
    logDebug ("Display hPa Pressure  = $disValue")
    return (float) disValue.round(2)
}    

private getBarometric (pres) {
    logTrace ("getBarometric ${pres}")
    if(displayFormat == "Imperial") {
        disValue = mbTOinhg(pres)
    } else { disValue = pres.toFloat()}
    logDebug ("Display Barometric Pressure  = $disValue")
    return (float) disValue.round(2)
}    

private getVisibility (dist) {
    logTrace ("getVisability ${dist}")
    if(displayFormat == "Imperial") {
        disValue = mTOmile(dist)
    } else {  disValue = mTOk(dist)}
    logDebug ("Display Visability  = $disValue")
    return (float) disValue.round(2)
}     

private getRain (amount) {
    logTrace ("getRain ${amount}")
    if(displayFormat == "Imperial") {
        disValue = mmTOin(amount)
    } else { disValue = amount.toFloat()}
    logDebug ("Display Rain  = $disValue")
    return (float) disValue.round(2)
}     

private getTime (num) {
    logTrace ("getTime ${num}")
    if(displayFormat == "Imperial") {
        disValue = mmTOin(amount)
    } else { disValue = amount.toFloat()}
    logDebug ("Display Rain  = $disValue")
    return (float) disValue.round(2)
}

/******************************************
     convertions
******************************************/
private cTOf(unit) {
    // Celsius to Fahrenheit
	unitI = unit.toFloat()
	theUnit = (unitI * (9/5) + 32)
	return (float) theUnit
}

private kTOf(unit) {
    // Kelvin to Fahrenheit
	unitI = unit.toFloat()
	theUnit = ((unitI -273.15) * (9/5) + 32)
	return theUnit
}

private kTOc(unit) {
    // Kelvin to Celsius
	unitI = unit.toFloat()
	theUnit = (unitI -273.15)
	return theUnit
}

private fTOc(unit) {
    // Fahrenheit to Celsius
	unitI = unit.toFloat()
	theUnit = (unitI - 32)*5/9
	return theUnit
}

private kphTOmph(unit){
    // KPH to MPH     
    unitI = unit.toFloat()           
    theUnit = (unitI * 0.621371).round(2)
	return theUnit              
}

private mpsTOmph(unit){
    // MPS to MPH     
    unitI = unit.toFloat()           
    theUnit = (unitI * 2.23694).round(2)
	return theUnit              
}

private hPaTOinhg(unit){
    // hPa to inHG     
    unitI = unit.toFloat()
    theUnit = (unitI * 0.029529983071445 ).round(2)
	return theUnit              
}

private mbTOinhg(unit){
    // MB to inHG     
    unitI = unit.toFloat()
    theUnit = ((unitI/100) * 0.02953).round(2)
	return theUnit              
}

private mTOmile(unit){
    // M to Miles     
    unitI = unit.toFloat()           
    theUnit = ((unitI * 3.28084) * 0.000189394).round(2)
	return theUnit              
}

private mTOk(unit){
    // M to K    
    unitI = unit.toFloat()           
    theUnit = (unitI * 0.001).round(2)
	return theUnit              
}

private mmTOin(unit){
    // MM to IN     
    unitI = unit.toFloat()           
    theUnit = (unitI / 25.4).round(2)
	return theUnit              
}

/******************************************
|   App Input Description Functions
*******************************************/
@Field volatile static Map<String,Map> tsDtMapFLD=[:]

static String logPrefix(String msg, String color = sNULL) {
    return span(appDescriptionFLD + "(v" + appVersionFLD + ") | ", sCLRGRY) + span(msg, color)
}

List logLevels() {
    List lItems = ["logInfo", "logWarn", "logDebug", "logError", "logTrace"]
    return settings?.findAll { it.key in lItems && it?.value == true }?.collect { it.key }
}

private void logDebug(String msg) { if((Boolean)settings.logDebug) { log.debug logPrefix(msg, "purple") } }
private void logInfo(String msg) { if((Boolean)settings.logInfo) { log.info sSPACE + logPrefix(msg, "#0299b1") } }
private void logTrace(String msg) { if((Boolean)settings.logTrace) { log.trace logPrefix(msg, sCLRGRY) } }
private void logWarn(String msg) { if((Boolean)settings.logWarn) { log.warn logPrefix(msg, sCLRORG) }}

void logError(String msg, Boolean noHist=false, ex=null) {
    if((Boolean)settings.logError) {
        log.error logPrefix(msg, sCLRRED)
        String a
        try {
            if (ex) a = getExceptionMessageWithLine(ex)
        } catch (ignored) {
        }
        if(a) log.error logPrefix(a, sCLRRED)
    }
}



def getHeaderAndFooter() {
    if(logEnable) log.debug "In getHeaderAndFooter (${appVersionDt})"
    def params = [
	    uri: "https://raw.githubusercontent.com/eralbright/eralbright/hubitat/headfoot.json",
		contentType: sAPPJSON,
		timeout: 20
	]
    
    try {
        def result = null
        httpGet(params) { resp ->
            state.headerMessage = resp.data.headerMessage
            state.footerMessage = resp.data.footerMessage
        }
        if(logEnable) log.debug "In getHeaderAndFooter - headerMessage: ${state.headerMessage}"
        if(logEnable) log.debug "In getHeaderAndFooter - footerMessage: ${state.footerMessage}"
    }
    catch (e) {
        state.headerMessage = "<div style='color:#1A77C9'>Kokopelli Home Automation</div>"
        state.footerMessage = "<div style='color:#1A77C9;text-align:center'>ERA6515<br>Kokopelli<br>$appVersion : $appVersionDt</div>"
    }
}

private Long wnow(){ return (Long)now() }

//// editing
/*
// Custom versions of the root objects above
static String spanBld(String str, String clr=sNULL, String img=sNULL)      { return str ? spanImgStr(img) + span(str, clr, sNULL, true)             : sBLANK }
static String spanBldBr(String str, String clr=sNULL, String img=sNULL)    { return str ? spanImgStr(img) + span(str, clr, sNULL, true, true)       : sBLANK }
static String spanBr(String str, String clr=sNULL, String img=sNULL)       { return str ? spanImgStr(img) + span(str, clr, sNULL, false, true)      : sBLANK }
static String spanSm(String str, String clr=sNULL, String img=sNULL)       { return str ? spanImgStr(img) + span(str, clr, sSMALL)                 : sBLANK }
static String spanSmBldUnd(String str, String clr=sNULL, String img=sNULL) { return str ? spanImgStr(img) + span(strUnder(str), clr, sSMALL, true) : sBLANK }
static String spanSmBldBr(String str, String clr=sNULL, String img=sNULL)  { return str ? spanImgStr(img) + span(str, clr, sSMALL, true, true)     : sBLANK }
static String spanMd(String str, String clr=sNULL, String img=sNULL)       { return str ? spanImgStr(img) + span(str, clr, sMEDIUM)                : sBLANK }
static String spanMdBr(String str, String clr=sNULL, String img=sNULL)     { return str ? spanImgStr(img) + span(str, clr, sMEDIUM, false, true)   : sBLANK }
static String spanMdBld(String str, String clr=sNULL, String img=sNULL)    { return str ? spanImgStr(img) + span(str, clr, sMEDIUM, true)          : sBLANK }
static String spanMdBldBr(String str, String clr=sNULL, String img=sNULL)  { return str ? spanImgStr(img) + span(str, clr, sMEDIUM, true, true)    : sBLANK }

divSmBld
static String divBld(String str, String clr=sNULL, String img=sNULL)        { return str ? div(spanImgStr(img) + span(str), clr, sNULL, true, false)   : sBLANK }
static String divBldBr(String str, String clr=sNULL, String img=sNULL)      { return str ? div(spanImgStr(img) + span(str), clr, sNULL, true, true)    : sBLANK }
static String divBr(String str, String clr=sNULL, String img=sNULL)         { return str ? div(spanImgStr(img) + span(str), clr, sNULL, false, true)   : sBLANK }
static String divSm(String str, String clr=sNULL, String img=sNULL)         { return str ? div(spanImgStr(img) + span(str), clr, sSMALL)              : sBLANK }
static String divSmBr(String str, String clr=sNULL, String img=sNULL)       { return str ? div(spanImgStr(img) + span(str), clr, sSMALL, false, true) : sBLANK }
static String divSmBld(String str, String clr=sNULL, String img=sNULL)      { return str ? div(spanImgStr(img) + span(str), clr, sSMALL, true)        : sBLANK }
static String divSmBldBr(String str, String clr=sNULL, String img=sNULL)    { return str ? div(spanImgStr(img) + span(str), clr, sSMALL, true, true)  : sBLANK }

//static String actChildName(){ return "Echo Speaks - Actions" }
//static String zoneChildName(){ return "Echo Speaks - Zones" }
//static String zoneChildDeviceName(){ return "Echo Speaks - Zones" }
static String documentationUrl() { return "httpERAs://tonesto7.github.io/echo-speaks-docs" }
static String videoUrl() { return "https://www.youtube.com/watch?v=wQPPlTFaGb4&ab_channel=SimplySmart123%E2%9C%85" }
static String textDonateUrl() { return "httpERAs://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HWBN4LB9NMHZ4" }
def updateDocsInput() { href url: documentationUrl(), style: sEXTNRL, required: false, title: inTS1("View Documentation", "documentation"), description: inactFoot(sTTP) }

String getAppEndpointUrl(subPath)   { return "${getApiServerUrl()}/${getHubUID()}/apps/${app?.id}${subPath ? "/${subPath}" : sBLANK}?access_token=${state.accessToken}".toString() }

String getLocalEndpointUrl(subPath) { return "${getLocalApiServerUrl()}/apps/${app?.id}${subPath ? "/${subPath}" : sBLANK}?access_token=${state.accessToken}" }


*/

/*
@Field static final String sBULLET        = '\u2022'
@Field static final String sBULLETINV     = '\u25E6'
@Field static final String sSQUARE        = '\u29C8'
@Field static final String sPLUS          = '\u002B'
@Field static final String sRIGHTARR      = '\u02C3'
@Field static final String okSymFLD       = "\u2713"
@Field static final String notOkSymFLD    = "\u2715"
@Field static final String sPAUSESymFLD   = "\u275A\u275A"
@Field static final String sFALSE         = 'false'
@Field static final String sTRUE          = 'true'
@Field static final String sENUM          = 'enum'
@Field static final String sNUMBER        = 'number'
@Field static final String sTEXT          = 'text'
@Field static final String sTIME          = 'time'
@Field static final String sMODE          = 'mode'
@Field static final String sCOMPLT        = 'complete'
@Field static final String sMEDIUM        = 'medium'
@Field static final String sTTC           = 'Tap to configure...'
@Field static final String sUFHA          = 'Varibles used for house averages to be passed to other programs'
//@Field static final String sTTCR          = 'Tap to configure (Required)'
//@Field static final String sTTP           = 'Tap to proceed...'
//@Field static final String sTTS           = 'Tap to select...'
@Field static final String sRESET         = 'reset'
@Field static final String sEXTNRL        = 'external'
@Field static final String sSWITCH        = 'switch'
@Field static final String sCHKBOX        = 'checkbox'
@Field static final String sCOMMAND       = 'command'
@Field static final String zoneHistFLD    = 'zoneHistory'
@Field static final List<String> lSUNRISESET   = ["sunrise", "sunset"]["Imperial", "Metric"]
*/

/*
static String getPublicImg(String imgName) { return "http://192.168.1.147/local/${imgName}.jpg" }
//static String sectTS(String t, String i = sNULL, Boolean bold=false) { return """<h3>${i ? """<img src="${i}" width="48"> """ : sBLANK} ${bold ? "<b>" : sBLANK}${t?.replaceAll("\\n", "<br>")}${bold ? "</b>" : sBLANK}</h3>""" }

public static String paraTS(String title = sNULL, String body = sNULL, String img = sNULL, Map tOpts=[s: 'normal', c: 'black', b: true, u:true], Map bOpts = [s:'normal', c: sNULL, b: false]) {
    String s = sBLANK
    s += title ? "<div style='${tOpts && (String)tOpts.c != sNULL ? "color: ${(String)tOpts.c};" : sBLANK}${tOpts && (String)tOpts.s != sNULL ? "font-size: ${(String)tOpts.s};" : sBLANK}${tOpts && (Boolean)tOpts.b ? "font-weight: bold;" : sBLANK}${tOpts && (Boolean)tOpts.u ? "text-decoration: underline;" : sBLANK}'>${img != sNULL ? """<img src=${getAppImg(img)} width="42"> """ : sBLANK}${title}</div>" : sBLANK
    s += body ? "<div style='${bOpts && (String)bOpts.c != sNULL ? "color: ${(String)bOpts.c};" : sBLANK}${bOpts && (String)bOpts.s != sNULL ? "font-size: ${(String)bOpts.s};" : sBLANK}${bOpts && (Boolean)bOpts.b ? "font-weight: bold;" : sBLANK}'>${body}</div>" : sBLANK
    return s
}

static String sectHead(String str, String img = sNULL) { return str ? "<h3 style='margin-top:0;margin-bottom:0;'>" + spanImgStr(img) + span(str, "darkorange", sNULL, true) + "</h3>" + "<hr style='background-color:${sCLRGRY};font-style:italic;height:1px;border:0;margin-top:0;margin-bottom:0;'>" : sBLANK }
static String sTS(String t, String i = sNULL, Boolean bold=false) { return "<h3>${i ? "<img src='${i}' width='42'> " : sBLANK} ${bold ? "<b>" : sBLANK}${t?.replaceAll("\n", "<br>")}${bold ? "</b>" : sBLANK}</h3>" }
static String s3TS(String t, String st, String i = sNULL, String c=sCLR4D9) { return "<h3 style='color:${c};font-weight: bold;'>${i ? "<img src='${i}' width='42'> " : sBLANK} ${t?.replaceAll("\n", "<br>")}</h3>${st ? "${st}" : sBLANK}" }
static String pTS(String t, String i = sNULL, Boolean bold=true, String color=sNULL) { return "${color ? "<div style='color: $color;'>" : sBLANK}${bold ? "<b>" : sBLANK}${i ? "<img src='${i}' width='42'> " : sBLANK}${t?.replaceAll("\n", "<br>")}${bold ? "</b>" : sBLANK}${color ? "</div>" : sBLANK}" }

static String inTS1(String str, String img = sNULL, String clr=sNULL, Boolean und=true) { return spanSmBldUnd(str, clr, img) }
static String inTS(String str, String img = sNULL, String clr=sNULL, Boolean und=true) { return divSm(strUnder(str?.replaceAll("\n", sSPACE)?.replaceAll("<br>", sSPACE), und), clr, img) }

static String divImgStr(String str, String img=sNULL) { return str ? div(img ? spanImg(img) + span(str) : str) : sBLANK }
static String getOkOrNotSymHTML(Boolean ok) { return ok ? span("(${okSymFLD})", sCLRGRN2) : span("(${notOkSymFLD})", sCLRRED2) }
static String inactFoot(String str) { return str ? inputFooter(str, sCLRGRY, true) : sBLANK }
static String optPrefix() { return spanSm(" (Optional)", "violet") }
*/
