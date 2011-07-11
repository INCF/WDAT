Q: What should the history object contain?
===

A: The tree usually doesn't need history support. We are going to keep
   it that way. When the main window in the explorer mode undergoes a
   change, we shall add a history item. The explorer path can be
   derived from the main path supplied. 

   Based on the above, a probable history fragment structure could be:
        #explore:main_path=/block_0/segment_7/recording_channel_9
   anything else is not inculded in the history.
