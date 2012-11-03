title='Integrator'
label='Integrator'
icon='integrator.png'

params=[
    ('name','Name',str,'Name of the integrator'),
    ('neurons','Number of neurons',int,'Number of neurons in the integrator'),
    ('dimensions','Number of dimensions',int,'Number of dimensions for the integrator'),
    ('tau_feedback','Feedback time constant [s]',float,'Synaptic time constant of the integrative feedback, in seconds (longer -> slower change but better value retention)'),
    ('tau_input','Input time constant [s]',float,'Synaptic time constant of the integrator input, in seconds (longer -> more input filtering)'),
    ('scale','Scaling factor',float,'A scaling value for the input (controls the rate of integration)'),
    ]

def test_params(net,p):
    try:
       net.network.getNode(p['name'])
       return 'That name is already taken'
    except:
        pass
    if p['neurons']<1: return 'Must have a positive number of neurons'
    if p['dimensions']<1: return 'Must have at least one dimension'
    
import numeric
def make(net,name='Integrator',neurons=100,dimensions=1,tau_feedback=0.1,tau_input=0.01,scale=1):
    if (dimensions<8):
        integrator=net.make(name,neurons,dimensions)
    else:
        integrator=net.make_array(name, int(neurons/dimensions),dimensions, quick=True)
    net.connect(integrator,integrator,pstc=tau_feedback)
    integrator.addDecodedTermination('input',numeric.eye(dimensions)*tau_feedback*scale,tau_input,False)
