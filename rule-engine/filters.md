# Filters

## Overview

| **Name** | **Description** |
| --- | --- |
| Calendar | Discards commands if current server time doesn't match specified cron calendar expression. |
| Metric | Discards commands with metric name not equal to the metric name specified in the rule. |
| Time | Discards commands with time that deviates by more than specified interval from the current server time. |
| Previous Value | Discards commands timestamped earlier than the time of the last (most recent) event in the given window. |
| Entity | Discards commands for entity not equal to one of entities specified in the rule. |
| Entity Group | Discards commands for entities that do not belong to one of entity groups specified in the rule. |
| Command | Discards commands for which the filter expression specified in the rule evaluates to false. |

## Calendar Filter

| **Name** | **Example** |
| --- | --- |
| cron | `* 8-18 * * MON-FRI` |
| cron AND | `'* 8-10 * * MON-FRI' AND '* 16-18 * * MON-FRI'` |
| cron OR | `'* 0-7,19-23 * * MON-FRI' OR '* * * * SUN, SAT'` |

## Metric Filter

## Time Filter

## Previous Value Filter

## Entity Filter

## Entity Group Filter

## Command Filter
