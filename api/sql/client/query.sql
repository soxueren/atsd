SELECT datetime, entity, value
  FROM cpu_busy
WHERE datetime > now - 1*minute
  LIMIT 3