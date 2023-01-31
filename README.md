# Load balancers

Project include three basic thread-safe load balancer implementations:

* `RANDOM` - just return randomly chosen node
* `ROUND ROBIN` - infinitely iterates over the available nodes returning the next one
* `WEIGHTED` - works just as `ROUND ROBIN`, but keeps decreasing a counter for each returned weighted node, 
so the node with zeroed counter is passed by at the next iteration. When all counters are zeroed,
balancer sets them again to whe weight values and begins iteration from the very first node.
This way, if `Ws` is the sum of all nodes' weights (`W1`, `W2`, .., `Wn`), in `Ws` cases each node 
will be called `Wn` times, i.e. according to its weight.

Load balancers allow adding and removing nodes. In order to change a node`s weight, 
a client should first remove, then add the same node with a new weight value.

> **_NOTE:_** Carefully fork or copy, because it is still necessary to test and review these implementations.


