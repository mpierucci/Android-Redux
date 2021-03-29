# Android-Redux
Redux-like unidirectional data flow architecture proposal for android applications.

This architecture borrow concepts from the known Redux architecture, such as `Stores`, `Middleware` but is not a one to one mapping from it does not encourage to hold
a single state for the hole application, and thus a single store. It rathers relies split the state  per "screens" (activities, fragments, composables) and uses 
`androix Viewmodels` as the bases for `Stores`, so we handle common issues tied to the Android framework.

## Disclaimer

This architecture relies on emmiting view states offen, so a UI layer that supports partial updates is recomended (Compose, RecyclerView) or at least complex screen with deep nested layouts should be avoided.
