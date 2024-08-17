# Define a task to publish artifacts to the local Maven repository
# The -PskipSigning parameter is used to skip the signing process as it's not required for local use
publishToMavenLocal:
	gradle publishToMavenLocal -PskipSigning

# Define a task to publish plugins to a portal/repository
# Similarly, the -PskipSigning parameter skips the signing process, simplifying the publishing workflow
publishToPortal:
	gradle publishPlugins -PskipSigning
