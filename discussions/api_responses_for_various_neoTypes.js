AnalogSignal
{
    "neo_id": "analogsignal_3", 
    "name": "AS-3", 
    "analogsignalarray": "", 
    "t_start": {
        "units": "ms", 
        "data": 300.0
    }, 
    "recordingchannel": "",
    "sampling_rate": {
        "units": "Hz", 
        "data": 20000.0
    },
    "segment": "segment_5",
    "signal": {
        "units": "mV", 
        "data": [12.199999999999999,
                 12.699999999999999, 
                 19.0, 
                 7.8099999999999996, 
                 3.4199999999999999, 
                 9.2799999999999994, 
                 -5.8600000000000003]
    }
}

SpikeTrain

{
    "neo_id": "spiketrain_4",
    "t_start": {
        "units": "ms", 
        "data": -400.0
    }, 
    "times": {
        "units": "ms", 
        "data": [-4.8799999999999999, 
                 3.4199999999999999,
                 2.4399999999999999]
    }, 
    "waveforms": [
        {
            "waveform_data": "[5.8600000000000003, -1.46, -0.48799999999999999, -7.3200000000000003, -9.7699999999999996, -12.699999999999999, -12.699999999999999]", 
            "channel_index": 0, 
            "waveform__unit": "mV"
        }, 
        {
            "waveform_data": "[45.859999999999999, -31.460000000000001, -0.33800000000000002, 67.319999999999993, -19.77, -109.7, -39.700000000000003]", 
            "channel_index": 1, 
            "waveform__unit": "mV"
        }], 
        "t_stop": {
            "units": "ms", 
            "data": 800.0
        }, 
        "segment": "segment_5", 
        "unit": "unit_3"
}

IRSAAnalogSignal

{
    "neo_id": "irsaanalogsignal_2", 
    "name": "ISAS-1", 
    "signal": {
        "units": "mV", 
        "data": [12.199999999999999, 
                 12.699999999999999, 
                 19.0, 
                 7.8099999999999996, 
                 3.4199999999999999, 
                 9.2799999999999994, 
                 -5.8600000000000003]
    }, 
    "t_start": {
        "units": "ms", 
        "data": -200.0
    }, 
    "times": {
        "units": "ms", 
        "data": [155.0, 
                 158.0, 
                 160.0, 
                 161.0, 
                 162.0, 
                 165.0, 
                 168.0]
    },
    "recordingchannel": "recordingchannel_3", 
    "segment": "segment_5"
}

Spike
{
    "neo_id": "spike_1",
    "waveforms": [
        {
            "waveform_data": "[5.8600000000000003, -1.46, -0.48799999999999999, -7.3200000000000003, -9.7699999999999996, -12.699999999999999, -12.699999999999999]", 
            "channel_index": 0, 
            "waveform__unit": "mV"
        }
    ],
    "time": {
        "units": "ms", 
        "data": 300.0
    }, 
    "left_sweep": {
        "units": "ms", 
        "data": 15.0
    },
    "sampling_rate": {
        "units": "kHz", 
        "data": 20.0
    }, 
    "segment": "segment_5", 
    "unit": "unit_3"
}

Event
{
    "segment": "segment_5",
    "time": {
        "units": "ms", 
        "data": 7.2199999999999998
    }, 
    "neo_id": "event_1", 
    "eventarray": "", 
    "label": "Stimuli end"
}

Epoch
{
    "neo_id": "epoch_2", 
    "label": "Displaying blue screen", 
    "epocharray": "",
    "time": {
        "units": "ms", 
        "data": 78.219999999999999
    }, 
    "duration": {
        "units": "ms", 
        "data": 0.34999999999999998
    }, 
    "segment": "segment_5"
}

Common :
"neo_id"
