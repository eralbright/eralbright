"""General dew_point constants."""
from homeassistant.const import Platform

DOMAIN = "dew_point"
PLATFORMS = [Platform.SENSOR]
CONF_TEMPERATURE_SENSOR = "temperature_sensor"
CONF_HUMIDITY_SENSOR = "humidity_sensor"
CONF_POLL = "poll"

DEFAULT_NAME = "Dew Point"
UPDATE_LISTENER = "update_listener"
