# Compaction Test

## Overview

Perform this compaction test to calculate ATSD storage efficiency and estimate disk space requirements for a custom dataset. 


## Instructions

* Open **Admin > Diagnostics > Compaction** page.
* Start compaction. Refresh the page until the task is completed.
* Open **Admin > Database Tables** page. Take note of the `atsd_d` table size in the column **Store File Size (MB)**.
* Insert your dataset. Make sure that the amount of data is 50+ megabytes or 1+ million samples.
* Execute the compaction again on **Compaction** page.
* Reload the **Database Tables** page and compare the difference in **Store File Size (MB)**.
* Divide the difference in size by the total number of samples in the dataset to calculate bytes/sample ratio.

