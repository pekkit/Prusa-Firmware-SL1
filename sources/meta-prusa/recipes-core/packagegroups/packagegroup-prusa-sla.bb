DESCRIPTION = "Basic task to get a device booting"
LICENSE = "MIT"
PR = "r9"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS_packagegroup-prusa-sla = "\
	etc sl1fw touch-ui \
	sl1fw-api sl1fw-fs wifi-config \
	prusa-connect-local \
	libva-v4l2-request weston \
	"

